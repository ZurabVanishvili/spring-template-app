package net.template.server.user.entity;

import lombok.Value;
import net.template.server.permission.entity.PermissionDto;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link User}
 */
@Value
public class UserDto implements Serializable {
    long id;
    String login;
    String password;
    Set<PermissionDto> permissions;
}