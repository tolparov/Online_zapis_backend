package ru.alliedar.pokaznoi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.user.User;
import ru.alliedar.pokaznoi.service.AuthService;
import ru.alliedar.pokaznoi.service.UserService;
import ru.alliedar.pokaznoi.web.dto.auth.*;
import ru.alliedar.pokaznoi.web.mappers.UserAuthMapper;
import ru.alliedar.pokaznoi.repository.UserRepository;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthMapper userAuthMapper;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public UserResponseDto login(UserLoginRequestDto userLoginRequestDto) {
        String email = userLoginRequestDto.getEmail();
        String password = userLoginRequestDto.getPassword();

        Optional<User> userOptional = userRepository.findByUsername(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return userAuthMapper.mapToDTO(user);
            } else {
                throw new IllegalArgumentException("Неверный пароль");
            }
        } else {
            throw new IllegalArgumentException("Пользователь не найден");
        }
    }

    @Override
    @Transactional
    public boolean resetPassword(UserResetPasswordDto userResetPasswordDto) {
        Optional<User> userOptional = userRepository.findByUsername(userResetPasswordDto.getEmail());
        return userOptional.map(user -> {
            user.setPassword(passwordEncoder.encode(userResetPasswordDto.getNewPassword()));
            userRepository.save(user);
            return true;
        }).orElseThrow(() -> new IllegalArgumentException("Пользователь с почтой " + userResetPasswordDto.getEmail() + " не существует."));
    }

    @Override
    @Transactional
    public boolean changePassword(UserChangePasswordDto userChangePasswordDto) {
        String userEmail = userChangePasswordDto.getEmail();
        String oldPassword = userChangePasswordDto.getOldPassword();
        String newPassword = userChangePasswordDto.getNewPassword();

        Optional<User> userOptional = userRepository.findByUsername(userEmail);
        return userOptional.map(user -> {
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                throw new IllegalArgumentException("Incorrect current password");
            }
            if (oldPassword.equals(newPassword)) {
                throw new IllegalArgumentException("New password must be different from the old one");
            }

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }).orElseThrow(() -> new IllegalArgumentException("User with email " + userEmail + " not found"));
    }


}
