package net.template.server.permission.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.template.server.permission.entity.Permission;


/**
 * DTO for {@link Permission}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PermissionDto {

    private long id;
    private String code;
    private String description;
}