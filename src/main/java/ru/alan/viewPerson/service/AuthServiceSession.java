package ru.alan.viewPerson.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthServiceSession {
	void createAndSetSessionCookie(HttpServletRequest request, HttpServletResponse response, Long value);

	boolean checkSessionCookie(HttpServletRequest request, HttpServletResponse response);
}
