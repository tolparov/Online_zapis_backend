package ru.alliedar.pokaznoi.web.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.alliedar.pokaznoi.domain.exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.Collections;

@AllArgsConstructor
public class CookieAuthFilter extends OncePerRequestFilter {

    private final StringRedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieValue = cookie.getValue();
                if (redisTemplate.opsForValue().get(cookieValue) != null) {
                    try {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        cookieValue, null,
                                        Collections.emptyList());
                        SecurityContextHolder.getContext()
                                .setAuthentication(authToken);
                    } catch (ResourceNotFoundException ignored) {

                    }
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
