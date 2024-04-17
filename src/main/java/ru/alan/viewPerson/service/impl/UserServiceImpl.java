package ru.alan.viewPerson.service.impl;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alan.viewPerson.dto.user.UserLoginRequestDto;
import ru.alan.viewPerson.dto.user.UserRequestDto;
import ru.alan.viewPerson.dto.user.UserResponseDto;
import ru.alan.viewPerson.entity.UserEntity;
import ru.alan.viewPerson.mapper.UserMapper;
import ru.alan.viewPerson.repository.UserRepository;
import ru.alan.viewPerson.service.UserService;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;

	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String login) {
		return (UserDetails) userRepository.findByEmail(login)
				.orElseThrow(() -> {
					throw new IllegalArgumentException("User with email:" + login + " not found.");
				});
	}



//	@Override
//	@Transactional(readOnly = true)
//	public UserResponseDto findByEmail(String email) {
//		return userRepository.findByEmail(email)
//				.map(userMapper::mapToDTO)
//				.orElseThrow(() -> {
//					throw new IllegalArgumentException("User with email=" + email + " not found.");
//				});
//	}

	@Override
	@Transactional
	public UserResponseDto create(UserRequestDto userRequestDto) {
		try {
			UserEntity user = userRepository.save(userMapper.mapToEntity(userRequestDto));
			return userMapper.mapToDTO(user);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("User with email "
					+ userRequestDto.getEmail() + " already exists.");
		}
	}

	@Override
	@Transactional
	public UserResponseDto login(UserLoginRequestDto userLoginRequestDto) {
		String email = userLoginRequestDto.getEmail();
		String password = userLoginRequestDto.getPassword();

		Optional<UserEntity> userOptional = userRepository.findByEmail(email);
		if (userOptional.isPresent()) {
			UserEntity user = userOptional.get();
			if (passwordEncoder.matches(password, user.getPassword())) {
				return userMapper.mapToDTO(user);
			} else {
				throw new IllegalArgumentException("Неверный пароль");
			}
		} else {
			throw new IllegalArgumentException("Пользователь не найден");
		}
	}




}
