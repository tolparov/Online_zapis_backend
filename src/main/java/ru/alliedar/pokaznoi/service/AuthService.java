package ru.alliedar.pokaznoi.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.web.dto.auth.*;

public interface AuthService {

    UserResponseDto create(UserRequestDto userRequestDto);
    UserResponseDto login(UserLoginRequestDto userLoginRequestDto);
    boolean resetPassword(UserResetPasswordDto userResetPasswordDto);
    boolean changePassword(UserChangePasswordDto userChangePasswordDto);
}
