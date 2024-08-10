package com.example.TechBuzzFeed.repository;

import com.example.TechBuzzFeed.entity.Answer;
import com.example.TechBuzzFeed.entity.User;
import com.example.TechBuzzFeed.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository  extends JpaRepository<Vote,Long> {
    Optional<Vote> findByUserAndAnswer(User user, Answer answer);

}
