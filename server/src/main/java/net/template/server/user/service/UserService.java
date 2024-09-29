package net.template.server.user.service;


import net.template.server.permission.entity.Permission;
import net.template.server.permission.repository.PermissionRepository;
import net.template.server.role.entity.Role;
import net.template.server.role.repository.RoleRepository;
import net.template.server.security.util.SecurityUtil;
import net.template.server.user.entity.User;
import net.template.server.user.mapper.UserMapper;
import net.template.server.user.model.UserDTO;
import net.template.server.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    // TODO: 9/29/2024  3.create table grid for user data
    private final UserRepository repository;

    private final UserMapper mapper;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public UserService(UserRepository repository, UserMapper mapper, RoleRepository roleRepository,
                       PermissionRepository permissionRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public UserDTO findById(long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow());
    }

    public List<UserDTO> loadAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return repository.findAll(pageable).map(mapper::toDTO).stream().toList();
    }


    public UserDTO save(UserDTO dto) {
        User user = mapper.toEntity(dto);

        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            Set<Role> resolvedRoles = user.getRoles().stream()
                    .map(role -> roleRepository.findById(role.getId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    String.format("Role with ID %d not found", role.getId()))))
                    .collect(Collectors.toSet());
            user.setRoles(resolvedRoles);
        }

        if (user.getPermissions() != null) {
            Set<Permission> permissions = user.getPermissions().stream()
                    .map(permission -> permissionRepository.findById(permission.getId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                    String.format("Permission with ID %d not found", permission.getId()))))
                    .collect(Collectors.toSet());
            user.setPermissions(permissions);
        }

        if (user.getId() <= 0) {
            repository.findByLoginAndIdNot(user.getLogin(), user.getId())
                    .ifPresent(existingUser -> {
                        throw new ResponseStatusException(HttpStatus.CONFLICT,
                                String.format("User with login %s already exists", user.getLogin()));
                    });
        }

        return mapper.toDTO(repository.save(user));
    }


    public User getCurrentUser() {
        String currUserLogin = SecurityUtil.getCurrentUsername();
        if (currUserLogin == null) {
            return null;
        }
        return repository.findByLogin(currUserLogin).orElse(null);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
