package ru.alan.viewPerson.mapper;

import org.mapstruct.Mapper;
import ru.alan.viewPerson.dto.user.UserRequestDto;
import ru.alan.viewPerson.dto.user.UserResponseDto;
import ru.alan.viewPerson.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserResponseDto mapToDTO(UserEntity user);
	UserEntity mapToEntity(UserRequestDto requestDto);
}
