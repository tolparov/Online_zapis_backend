package ru.alliedar.pokaznoi.service.impl;

import org.springframework.stereotype.Service;
import ru.alliedar.pokaznoi.service.AuthService;
import ru.alliedar.pokaznoi.web.dto.auth.JwtRequest;
import ru.alliedar.pokaznoi.web.dto.auth.JwtResponse;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        return null;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return null;
    }
}
