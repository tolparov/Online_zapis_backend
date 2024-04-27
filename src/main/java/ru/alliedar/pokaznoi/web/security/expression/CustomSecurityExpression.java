package ru.alliedar.pokaznoi.web.security.expression;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.alliedar.pokaznoi.domain.user.Role;
import ru.alliedar.pokaznoi.service.UserService;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final UserService userService;
    private final StringRedisTemplate stringRedisTemplate;

    public boolean canAccessUser(final Long id) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        Long userId = -1L;
        String key = (String) authentication.getPrincipal();
        String value = stringRedisTemplate.opsForValue().get(key);

        if (value != null) {
            userId = Long.parseLong(value);
        }

        return userId.equals(id) || hasAnyRole(authentication, Role.ROLE_ADMIN);
    }

    private boolean hasAnyRole(final Authentication authentication,
                               final Role... roles) {
        for (Role role : roles) {
            SimpleGrantedAuthority authority =
                    new SimpleGrantedAuthority((role.name()));
            if (authentication.getAuthorities().contains(authority)) {
                return true;
            }
        }
        return false;
    }

    public boolean canAccessTask(final Long taskId) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        long userId = -1L;
        String key = (String) authentication.getPrincipal();
        String value = stringRedisTemplate.opsForValue().get(key);

        if (value != null) {
            userId = Long.parseLong(value);
        }

        return userService.isTaskOwner(userId, taskId);
    }

}
