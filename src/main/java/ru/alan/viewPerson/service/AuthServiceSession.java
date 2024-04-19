package ru.alan.viewPerson.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;
import ru.alan.viewPerson.dto.user.UserResponseDto;

public interface AuthServiceSession {
	void createAndSetSessionCookie(HttpServletRequest request, HttpServletResponse response, Long value);

	boolean checkSessionCookie(HttpServletRequest request, HttpServletResponse response);
}
