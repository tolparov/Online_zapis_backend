package ru.alan.viewPerson.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alan.viewPerson.service.AuthServiceSession;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AuthSessionServiceImpl implements AuthServiceSession {

	private final StringRedisTemplate stringRedisTemplate;

	public AuthSessionServiceImpl(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	@Override
	@Transactional
	public void createAndSetSessionCookie(HttpServletRequest request, HttpServletResponse response, Long value) {
		String key = UUID.randomUUID().toString();

		stringRedisTemplate.opsForValue().set(key, String.valueOf(value));

		Cookie cookie = new Cookie("sessionId", key);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	@Override
	@Transactional
	public boolean checkSessionCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("sessionId")) {
					String sessionId = cookie.getValue();

					boolean existsInRedis = stringRedisTemplate.hasKey(sessionId);
					if (existsInRedis) {
						stringRedisTemplate.delete(sessionId);

						cookie.setMaxAge(0);
						response.addCookie(cookie);

						return true;
					}
				}
			}
		}
		return false;
	}

}
