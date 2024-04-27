package ru.alliedar.pokaznoi.web.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookieAuthProvider {
    private final UserDetailsService userDetailsService;

    public Authentication getAuthentication(final String coockieValue) {
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(coockieValue);
        return new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());
    }

}
