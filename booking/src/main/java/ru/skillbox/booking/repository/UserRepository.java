package ru.skillbox.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.booking.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    boolean existsByNameOrEmail(String name, String email);

}
