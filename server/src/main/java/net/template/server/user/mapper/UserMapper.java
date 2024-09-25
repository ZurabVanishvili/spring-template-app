package net.template.server.user.mapper;

import net.template.server.user.entity.User;
import net.template.server.user.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserDTO dto);
}
