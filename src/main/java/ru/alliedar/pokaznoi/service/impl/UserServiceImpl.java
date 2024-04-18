package ru.alliedar.pokaznoi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.exception.ResourceNotFoundException;
import ru.alliedar.pokaznoi.domain.user.User;
import ru.alliedar.pokaznoi.repository.UserRepository;
import ru.alliedar.pokaznoi.service.UserService;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public User getByUsername(String username) {
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.update(user);
        return user;
    }

    @Override
    @Transactional
    public User create(User user) {
        if (userRepository.findByUserName(user.getUsername()).isPresent()) {
            throw new IllegalStateException("User already exist");
        }
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation do not match.");
        }

        userRepository.create(user);
        userRepository.findById(user.getId());
        return null;
    }

    @Override
    @Transactional
    public boolean isTaskOwner(Long userId, Long taskId) {
        return userRepository.isTaskOwner(userId, taskId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }
}
