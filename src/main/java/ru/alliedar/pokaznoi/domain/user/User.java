package ru.alliedar.pokaznoi.domain.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.alliedar.pokaznoi.domain.task.Task;

import java.io.Serializable;
import java.util.Set;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;
    private String password;

    @Transient
    private String passwordConfirmation;

    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles")
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles;

    @CollectionTable(name = "users_task")
    @OneToMany
    @JoinColumn(name = "task_id")
    private List<Task> tasks;

}
