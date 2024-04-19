package ru.alan.viewPerson.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alan.viewPerson.dto.user.*;
import ru.alan.viewPerson.service.AuthServiceSession;
import ru.alan.viewPerson.service.UserService;

@RestController
@RequestMapping
@CrossOrigin
public class AuthController {
	private final UserService userService;
	private final AuthServiceSession authServiceSession;

	public AuthController(UserService userService, AuthServiceSession authServiceSession) {
		this.userService = userService;
		this.authServiceSession = authServiceSession;
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
			authServiceSession.createAndSetSessionCookie(request, response, user.getId());
			return ResponseEntity.ok(user);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {
		boolean sessionValid = authServiceSession.checkSessionCookie(request, response);

		if (sessionValid) {
			return ResponseEntity.ok("Выход успешен");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Незарегистророван");
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<String> resetPassword(@RequestBody UserResetPasswordDto userResetPasswordDto) {
		if (userService.resetPassword(userResetPasswordDto)) {
			return ResponseEntity.ok("Пароль успешно сброшен");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с указанным адресом электронной почты не найден");

	}

	@PostMapping("/changePassword")
	public ResponseEntity<String> changePassword(HttpServletRequest request, HttpServletResponse response, @RequestBody UserChangePasswordDto userChangePasswordDto) {
		try {

			boolean sessionValid = authServiceSession.checkSessionCookie(request, response);

			if (sessionValid) {
				if (userService.changePassword(userChangePasswordDto)) {
					return ResponseEntity.ok("Пароль успешно изменен");
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Пользователь с указанным адресом электронной почты не найден");
				}
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Недействительная сессия");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка при изменении пароля: " + e.getMessage());
		}
	}

}

