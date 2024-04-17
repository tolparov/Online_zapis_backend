package ru.alan.viewPerson.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.web.bind.annotation.*;
import ru.alan.viewPerson.config.SecurityConfig;
import ru.alan.viewPerson.dto.user.UserLoginRequestDto;
import ru.alan.viewPerson.dto.user.UserRequestDto;
import ru.alan.viewPerson.dto.user.UserResponseDto;
import ru.alan.viewPerson.service.UserService;

@RestController
@RequestMapping
public class AuthController {
	private final UserService userService;
	private final SecurityConfig securityConfig;
	private final SessionRepository sessionRepository;

	public AuthController(UserService userService, SecurityConfig securityConfig, SessionRepository sessionRepository) {
		this.userService = userService;
		this.securityConfig = securityConfig;
		this.sessionRepository = sessionRepository;
	}

	@PostMapping("/register")
	public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto userRequestDto) {
		UserResponseDto newUser = userService.create(userRequestDto);
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<UserResponseDto> loginUser(@RequestBody UserLoginRequestDto userLoginRequestDto, HttpServletResponse response, HttpServletRequest request) {
		try {
			UserResponseDto user = userService.login(userLoginRequestDto);

			Session session = sessionRepository.createSession();
			session.setAttribute("userId", user.getId());
			securityConfig.addCookie(request, response, "sessionId", session.getId());

			return ResponseEntity.ok(user);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}


}

