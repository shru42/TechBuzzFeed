package com.example.TechBuzzFeed.repository;

import com.example.TechBuzzFeed.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findByName(String name);

}
