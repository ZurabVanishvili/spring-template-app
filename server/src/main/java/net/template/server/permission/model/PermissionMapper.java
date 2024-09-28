package net.template.server.permission.model;

import net.template.server.permission.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toEntity(PermissionDto dto);

    PermissionDto toDto(Permission entity);
}
