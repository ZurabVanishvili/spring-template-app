package net.template.server.role.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.template.server.permission.entity.PermissionDto;
import net.template.server.user.entity.UserDto;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private long id;
    private String code;
    private Set<UserDto> users;
    private Set<PermissionDto> permissions;
}
