package net.template.server.role.model;

import net.template.server.role.entity.Role;
import net.template.server.user.entity.User;
import net.template.server.user.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RoleModelMapper {


    @Mapping(target = "users", qualifiedByName = "mapWithoutRoles")
    RoleDTO toDto(Role role);

    @Named("mapWithoutRoles")
    @Mapping(target = "roles", ignore = true)
    UserDTO toRoleDTO(User role);

    Role toEntity(RoleDTO dto);


}
