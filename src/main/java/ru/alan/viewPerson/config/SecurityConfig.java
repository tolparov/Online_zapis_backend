package ru.alan.viewPerson.config;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.SessionRepository;

@Configuration
public class SecurityConfig {
	private final SessionRepository sessionRepository;
	private final StringRedisTemplate stringRedisTemplate;

	public SecurityConfig(SessionRepository sessionRepository, StringRedisTemplate stringRedisTemplate) {
		this.sessionRepository = sessionRepository;
		this.stringRedisTemplate = stringRedisTemplate;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests((authz) ->
						authz
								.requestMatchers(HttpMethod.POST, "/register").permitAll()
								.requestMatchers("/index.html", "/", "/bank", "/login", "*.js", "*.css", "*.ico", "/test", "*.html").permitAll()
								.anyRequest().permitAll() // Разрешаем доступ ко всем URL-адресам
				)
				.httpBasic(Customizer.withDefaults());
		return http.build();
	}


	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	public void addCookie(HttpServletRequest request, HttpServletResponse response, String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		response.addCookie(cookie);
		stringRedisTemplate.opsForValue().set(key, value);
	}



}
