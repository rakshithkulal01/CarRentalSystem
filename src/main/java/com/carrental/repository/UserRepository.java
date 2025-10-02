package com.carrental.repository;

import com.carrental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Spring Data JPA will automatically generate the implementation for this method.
    // It understands that "findByEmail" means "SELECT * FROM users WHERE email = ?"
    Optional<User> findByEmail(String email);
}
