package net.template.server.user.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import net.template.server.security.util.SecurityUtil;

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

    public void setPassword(String password) {
        if (password != null && !password.isEmpty()) {
            this.password = SecurityUtil.encodePassword(password);
        }
    }
}
