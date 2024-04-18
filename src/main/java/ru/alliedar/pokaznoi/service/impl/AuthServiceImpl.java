package ru.alliedar.pokaznoi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.alliedar.pokaznoi.domain.user.User;
import ru.alliedar.pokaznoi.service.AuthService;
import ru.alliedar.pokaznoi.service.UserService;
import ru.alliedar.pokaznoi.web.dto.auth.JwtRequest;
import ru.alliedar.pokaznoi.web.dto.auth.JwtResponse;
import ru.alliedar.pokaznoi.web.security.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        // Заходим, сравниваем пароли
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        // Выдаем токены
        User user = userService.getByUsername(loginRequest.getUsername());
        jwtResponse.setId(user.getId());
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(user.getId(), user.getUsername(), user.getRoles()));
        jwtResponse.setAccessToken(jwtTokenProvider.createRefreshToken(user.getId(), user.getUsername()));
        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }
}
