package ru.alliedar.pokaznoi.service;

import ru.alliedar.pokaznoi.domain.user.User;
import ru.alliedar.pokaznoi.web.dto.auth.UserRequestDto;
import ru.alliedar.pokaznoi.web.dto.auth.UserResponseDto;

public interface UserService {

    User getById(Long id);

    User getByUsername(String username);

    User update(User user);

    UserResponseDto create(UserRequestDto user);

    boolean isTaskOwner(Long userId, Long taskId);

    void delete(Long id);

}
