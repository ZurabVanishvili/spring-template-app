package net.template.server.role.serivce;

import net.template.server.permission.entity.Permission;
import net.template.server.permission.model.PermissionDto;
import net.template.server.permission.repository.PermissionRepository;
import net.template.server.role.entity.Role;
import net.template.server.role.model.RoleDTO;
import net.template.server.role.model.RoleModelMapper;
import net.template.server.role.repository.RoleRepository;
import net.template.server.user.entity.User;
import net.template.server.user.model.UserDTO;
import net.template.server.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleModelMapper mapper;
    private final RoleRepository repository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleModelMapper mapper, RoleRepository repository,
                       UserRepository userRepository,
                       PermissionRepository permissionRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
    }


    public List<RoleDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public RoleDTO findById(Long aLong) {
        Optional<Role> roleOptional = repository.findById(aLong);
        if (roleOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role with id: %s not found!", aLong));
        }
        return mapper.toDto(roleOptional.get());
    }
    public RoleDTO save(RoleDTO roleDTO) {
        Role role = mapper.toEntity(roleDTO);

        repository.findByCodeAndIdNot(role.getCode(), role.getId())
                .ifPresent(existingRole -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            String.format("Role with code: %s already exists", roleDTO.getCode()));
                });

        if (role.getId() > 0) {
            role = repository.findById(role.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("Role with id: %s not found!", roleDTO.getId())));
        }

        Set<User> existingUsers = new HashSet<>();
        for (UserDTO userDto : roleDTO.getUsers()) {
            User user = userRepository.findById(userDto.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("User with id: %s not found!", userDto.getId())));
            existingUsers.add(user);
        }
        role.setUsers(existingUsers);

        Set<Permission> existingPermissions = new HashSet<>();
        for (PermissionDto permissionDto : roleDTO.getPermissions()) {
            Permission permission = permissionRepository.findById(permissionDto.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("Permission with id: %s not found!", permissionDto.getId())));
            existingPermissions.add(permission);
        }
        role.setPermissions(existingPermissions);

        return mapper.toDto(repository.save(role));
    }

}
