package ru.alliedar.pokaznoi.web.mappers;

import org.mapstruct.Mapper;
import ru.alliedar.pokaznoi.domain.user.User;
import ru.alliedar.pokaznoi.web.dto.user.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto dto);
}
