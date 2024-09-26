package net.template.server.permission.entity;

import java.io.Serializable;

/**
 * DTO for {@link Permission}
 */
public record PermissionDto(long id, String code, String description) implements Serializable {
}