package ru.alan.viewPerson.service.impl;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alan.viewPerson.dto.user.*;
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
			Optional<UserEntity> userOptional = userRepository.findByEmail(userRequestDto.getLogin());
			if (userOptional.isPresent()) {
				throw new IllegalArgumentException("Пользователь с логином "
						+ userRequestDto.getLogin() + " уже существует.");
			}
			UserEntity user = userRepository.save(userMapper.mapToEntity(userRequestDto));
			return userMapper.mapToDTO(user);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Пользователь с адресом электронной почты "
					+ userRequestDto.getEmail() + " уже существует.");
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
	@Override
	@Transactional
	public boolean resetPassword(UserResetPasswordDto userResetPasswordDto) {
		Optional<UserEntity> userOptional = userRepository.findByEmail(userResetPasswordDto.getEmail());
		return userOptional.map(user -> {
			user.setPassword(passwordEncoder.encode(userResetPasswordDto.getNewPassword()));
			userRepository.save(user);
			return true;
		}).orElseThrow(() -> new IllegalArgumentException("Пользователь с почтой " + userResetPasswordDto.getEmail() + " не существует."));
	}
	@Override
	@Transactional
	public boolean changePassword(UserChangePasswordDto userChangePasswordDto) {
		String userEmail = userChangePasswordDto.getEmail();
		String oldPassword = userChangePasswordDto.getOldPassword();
		String newPassword = userChangePasswordDto.getNewPassword();

		Optional<UserEntity> userOptional = userRepository.findByEmail(userEmail);
		return userOptional.map(user -> {
			if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
				throw new IllegalArgumentException("Incorrect current password");
			}
			if (oldPassword.equals(newPassword)) {
				throw new IllegalArgumentException("New password must be different from the old one");
			}

			user.setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(user);
			return true;
		}).orElseThrow(() -> new IllegalArgumentException("User with email " + userEmail + " not found"));
	}



}
