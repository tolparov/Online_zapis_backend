package ru.alliedar.pokaznoi.domain.user;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.alliedar.pokaznoi.domain.task.Task;

import java.util.Set;
import java.util.List;

@Data
public class User {

    private Long id;
    private String name;
    private String username;
    private String password;
    private String passwordConfirmation;
    private Set<Role> roles;
    private List<Task> tasks;

}
