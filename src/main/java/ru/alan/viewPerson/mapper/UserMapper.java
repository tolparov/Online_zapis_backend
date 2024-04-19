package ru.alan.viewPerson.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.alan.viewPerson.dto.user.UserRequestDto;
import ru.alan.viewPerson.dto.user.UserResponseDto;
import ru.alan.viewPerson.entity.Role;
import ru.alan.viewPerson.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(target = "roles", expression = "java(user.getRole().toString())")
	UserResponseDto mapToDTO(UserEntity user);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "role", constant = "USER")
	@Mapping(target = "password", source = "requestDto.password")
	UserEntity mapToEntity(UserRequestDto requestDto);
}
