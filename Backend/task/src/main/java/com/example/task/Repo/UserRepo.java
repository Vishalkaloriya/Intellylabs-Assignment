package com.example.task.Repo;

import com.example.task.Entity.User; // Change import to User entity
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> { // Change repository name and entity type to User
    Optional<User> findOneByEmailAndPassword(String email, String password); // Update method signature to work with User entity

    User findByEmail(String email); // Update method signature to work with User entity
}
