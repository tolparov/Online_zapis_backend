package ru.alan.viewPerson.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.alan.viewPerson.service.impl.UserServiceImpl;

@Configuration
public class SecurityConfig  {
	private final StringRedisTemplate stringRedisTemplate;
	private final UserServiceImpl userService;

	public SecurityConfig(StringRedisTemplate stringRedisTemplate, UserServiceImpl userService) {
		this.stringRedisTemplate = stringRedisTemplate;
		this.userService = userService;
	}



	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.httpBasic(AbstractHttpConfigurer::disable)
				.cors(Customizer.withDefaults())
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(sessionManagement ->
						sessionManagement
								.sessionCreationPolicy(
										SessionCreationPolicy.STATELESS
								)
				)
				.authorizeHttpRequests((authz) ->
						authz
								.requestMatchers("/auth/register", "/auth/login", "/auth/resetPassword").permitAll()
								.anyRequest().authenticated()
				)

				.addFilterBefore(new CoockieAuthFilter(stringRedisTemplate), UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
