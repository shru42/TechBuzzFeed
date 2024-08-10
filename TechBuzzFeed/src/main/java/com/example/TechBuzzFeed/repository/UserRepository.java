package com.example.TechBuzzFeed.repository;

import com.example.TechBuzzFeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("Select u from User u where u.name=:name")
    Optional<User> findByUsername(@Param("name") String name);
}
