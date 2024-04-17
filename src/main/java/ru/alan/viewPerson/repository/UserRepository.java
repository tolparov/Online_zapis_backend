package ru.alan.viewPerson.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alan.viewPerson.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByEmail(String email);


}
