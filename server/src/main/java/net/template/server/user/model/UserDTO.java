package net.template.server.user.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import net.template.server.permission.entity.PermissionDto;
import net.template.server.role.model.RoleDTO;
import net.template.server.security.util.SecurityUtil;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDTO {

    private long id;

    @NotNull(message = "Login should not be null")
    @NotEmpty(message = "Login should not be empty")
    private String login;

    @Min(value = 8, message = "Password should have at least 8 characters")
    @Max(value = 20, message = "Password should not exceed 20 characters")
    private String password;
    private Set<PermissionDto> permissions = new HashSet<>();

    private Set<RoleDTO> roles = new HashSet<>();


    public void setPassword(String password) {
        if (password != null && !password.isEmpty()) {
            this.password = SecurityUtil.encodePassword(password);
        }
    }
}
