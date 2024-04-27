package ru.alliedar.pokaznoi.web.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CookieUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String login) {
        return (UserDetails) userRepository.findByUsername(login)
                .orElseThrow(() -> new IllegalArgumentException(
                        "User with email:" + login + " not found."));
    }

}
