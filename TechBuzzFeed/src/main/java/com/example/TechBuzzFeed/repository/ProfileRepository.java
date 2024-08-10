package com.example.TechBuzzFeed.repository;

import com.example.TechBuzzFeed.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile,Long> {
    Profile findByUserId(Long userId);
}
