package by.kharchenko.springbootcafe.mapsrtuct;

import by.kharchenko.springbootcafe.dto.UserDTO;
import by.kharchenko.springbootcafe.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO userToUserDto(User user);
    User userDtoToUser(UserDTO userDTO);
}
