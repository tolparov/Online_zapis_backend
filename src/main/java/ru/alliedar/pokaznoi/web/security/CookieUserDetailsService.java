package ru.alliedar.pokaznoi.web.security;

import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.user.User;
import ru.alliedar.pokaznoi.repository.UserRepository;
import ru.alliedar.pokaznoi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookieUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) {
        return (UserDetails) userRepository.findByUsername(login)
                .orElseThrow(() -> new IllegalArgumentException("User with email:" + login + " not found."));
    }

}
