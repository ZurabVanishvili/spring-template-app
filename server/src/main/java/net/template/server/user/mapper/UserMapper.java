package net.template.server.user.mapper;

import net.template.server.role.entity.Role;
import net.template.server.role.model.RoleDTO;
import net.template.server.user.entity.User;
import net.template.server.user.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", qualifiedByName = "mapWithoutUsers")
    UserDTO toDTO(User user);
    @Named("mapWithoutUsers")
    @Mapping(target = "users", ignore = true)
    RoleDTO toRoleDTO(Role role);
    User toEntity(UserDTO dto);
}
