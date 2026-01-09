package com.example.securityfinal.user.repository;

import com.example.securityfinal.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReposetory extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String userName);
}
