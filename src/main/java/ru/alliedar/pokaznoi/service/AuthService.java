package ru.alliedar.pokaznoi.service;

import ru.alliedar.pokaznoi.web.dto.auth.UserChangePasswordDto;
import ru.alliedar.pokaznoi.web.dto.auth.UserLoginRequestDto;
import ru.alliedar.pokaznoi.web.dto.auth.UserResetPasswordDto;
import ru.alliedar.pokaznoi.web.dto.auth.UserResponseDto;

public interface AuthService {

    UserResponseDto login(UserLoginRequestDto userLoginRequestDto);

    boolean resetPassword(UserResetPasswordDto userResetPasswordDto);

    boolean changePassword(UserChangePasswordDto userChangePasswordDto);
}
