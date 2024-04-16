package ru.alliedar.pokaznoi.service;

import ru.alliedar.pokaznoi.web.dto.auth.JwtRequest;
import ru.alliedar.pokaznoi.web.dto.auth.JwtResponse;

public interface AuthService {

    JwtResponse login (JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);
}
