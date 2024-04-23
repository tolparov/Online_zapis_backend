package ru.alan.viewPerson.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;

public class CoockieAuthFilter extends OncePerRequestFilter {


	private final StringRedisTemplate redisTemplate;

	public CoockieAuthFilter(StringRedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieValue = cookie.getValue();
				if (redisTemplate.opsForValue().get(cookieValue) != null) {
					response.setStatus(HttpServletResponse.SC_OK);
					filterChain.doFilter(request, response); // Перемещаем вызов сюда
					return;
				}
			}
		}
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		filterChain.doFilter(request, response); // Перемещаем вызов сюда
	}
}

