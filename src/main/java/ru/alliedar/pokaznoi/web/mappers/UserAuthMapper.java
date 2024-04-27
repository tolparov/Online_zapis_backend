package ru.alliedar.pokaznoi.web.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.alliedar.pokaznoi.domain.user.Role;
import ru.alliedar.pokaznoi.domain.user.User;
import ru.alliedar.pokaznoi.web.dto.auth.UserRequestDto;
import ru.alliedar.pokaznoi.web.dto.auth.UserResponseDto;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserAuthMapper {
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto mapToDTO(final User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setEmail(user.getUsername());
        userResponseDto.setLogin(user.getName());
        userResponseDto.setRoles(Role.ROLE_USER); // TODO переделать в SET
        return userResponseDto;
    }

    public User mapToEntity(final UserRequestDto requestDto) {
        User user = new User();
        user.setId(requestDto.getId());
        user.setUsername(requestDto.getEmail());
        user.setName(requestDto.getLogin());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Set<Role> role = Set.of(Role.ROLE_USER);
        user.setRoles(role);
        return user;
    }
}
