package com.example.servicoderemessauser.repository;

import com.example.servicoderemessauser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);

    User findByDocument (String document);
}
