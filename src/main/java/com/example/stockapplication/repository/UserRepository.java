package com.example.stockapplication.repository;

import com.example.stockapplication.domain.UserDTO;
import com.example.stockapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Query("SELECT new com.example.stockapplication.domain.UserDTO(u.cccd, u.email, u.firstName, u.lastName, u.createdDate, u.lastUpdated) FROM User u")
    List<UserDTO> findAllUsers();
}
