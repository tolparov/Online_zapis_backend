package ru.alan.viewPerson.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.alan.viewPerson.dto.user.UserRequestDto;
import ru.alan.viewPerson.dto.user.UserResponseDto;
import ru.alan.viewPerson.entity.Role;
import ru.alan.viewPerson.entity.UserEntity;

import java.util.Set;

@Component
public class UserMapper {
	private final PasswordEncoder passwordEncoder;

	public UserMapper(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public UserResponseDto mapToDTO(UserEntity user) {
		UserResponseDto UserResponseDto = new UserResponseDto();
		UserResponseDto.setId(user.getId());
		UserResponseDto.setEmail(user.getEmail());
		UserResponseDto.setLogin(user.getLogin());
		UserResponseDto.setRoles(user.getRole());
		return UserResponseDto;
	}

	public UserEntity mapToEntity(UserRequestDto requestDto) {
		UserEntity user = new UserEntity();
		user.setId(requestDto.getId());
		user.setEmail(requestDto.getEmail());
		user.setLogin(requestDto.getLogin());
		user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
		user.setRole(Role.USER);
		return user;
	}
}
