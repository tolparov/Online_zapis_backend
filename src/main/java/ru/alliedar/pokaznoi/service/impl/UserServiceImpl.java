package ru.alliedar.pokaznoi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alliedar.pokaznoi.domain.exception.ResourceNotFoundException;
import ru.alliedar.pokaznoi.domain.user.User;
import ru.alliedar.pokaznoi.repository.UserRepository;
import ru.alliedar.pokaznoi.service.UserService;
import ru.alliedar.pokaznoi.web.dto.auth.UserRequestDto;
import ru.alliedar.pokaznoi.web.dto.auth.UserResponseDto;
import ru.alliedar.pokaznoi.web.mappers.UserAuthMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthMapper userAuthMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::getById", key = "#id")
    public User getById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::getByUsername", key = "#username")
    public User getByUsername(final String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional
    @Caching(put = {@CachePut(value = "UserService::getById", key = "#user.id"),
            @CachePut(value = "UserService::getByUsername",
                    key = "#user.username")})
    public User update(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    //    @Override
//    @Transactional
////    @Caching(cacheable = {@Cacheable(value = "UserService::getById",
//                                                          key = "#user.id"),
////            @Cacheable(value = "UserService::getByUsername",
//                                                    key = "#user.username")})
//    public User create(User user) {
//        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
//            throw new IllegalStateException("User already exists.");
//        }
//        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
//            throw new IllegalStateException(
//            "Password and password confirmation do not match.");
//        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        Set<Role> roles = Set.of(Role.ROLE_USER);
//        user.setRoles(roles);
//        userRepository.save(user);
//        return user;
//    }
    @Override
    @Transactional
    public UserResponseDto create(final UserRequestDto userRequestDto) {
        try {
            Optional<User> userOptional =
                    userRepository.findByUsername(userRequestDto.getLogin());
            if (userOptional.isPresent()) {
                throw new IllegalArgumentException("Пользователь с логином "
                        + userRequestDto.getLogin() + " уже существует.");
            }
            User user = userRepository.save(
                    userAuthMapper.mapToEntity(userRequestDto));
            return userAuthMapper.mapToDTO(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(
                    "Пользователь с адресом электронной почты "
                            + userRequestDto.getEmail() + " уже существует.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(key = "#userId + '.' + #taskId",
            value = "UserService::isTaskOwner")
    public boolean isTaskOwner(final Long userId, final Long taskId) {
        return userRepository.isTaskOwner(userId, taskId);
    }

    @Override
    @Transactional
    @CacheEvict(value = "UserService::getById", key = "#id")
    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

}
