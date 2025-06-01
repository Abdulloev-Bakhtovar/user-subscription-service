package ru.bakht.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bakht.userservice.entity.User;

import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);
}
