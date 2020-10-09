package com.morlimoore.piggybankapi.repositories;

import com.morlimoore.piggybankapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> getUserByEmail(String email);

//    Boolean existsByEmail(String email);
}
