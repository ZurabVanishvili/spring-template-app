package net.template.server.permission.service;

import net.template.server.permission.entity.Permission;
import net.template.server.permission.model.PermissionDto;
import net.template.server.permission.model.PermissionMapper;
import net.template.server.permission.repository.PermissionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    private final PermissionRepository repository;

    private final PermissionMapper mapper;

    public PermissionService(PermissionRepository repository, PermissionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PermissionDto findById(long id) {
        return mapper.toDto(repository.findById(id).orElseThrow());
    }

    public Set<PermissionDto> findAll() {
        return repository.findAll().parallelStream().map(mapper::toDto).collect(Collectors.toSet());
    }

    public List<PermissionDto> saveAll(List<PermissionDto> dtos) {
        List<Permission> permissions = dtos.stream().map(mapper::toEntity).toList();
        List<String> permCodes = repository.findAllPermissionCodes();

        for (Permission permission : permissions) {
            if (permCodes.contains(permission.getCode()) && permission.getId() != 0) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        String.format("Permission with code '%s' already exists ", permission.getCode()));
            }
        }

        return repository.saveAll(permissions).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public PermissionDto save(PermissionDto dto) {
        Permission entity = mapper.toEntity(dto);

        Optional<Permission> permissionOptional = repository.findByCodeAndIdNot(entity.getCode(), entity.getId());
        if (permissionOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Permission with code '%s' already exists ", entity.getCode()));
        }

        return mapper.toDto(repository.save(entity));
    }
}


