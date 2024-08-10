package com.example.TechBuzzFeed.repository;

import com.example.TechBuzzFeed.entity.Answer;
import com.example.TechBuzzFeed.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestion_QuestionId(Long questionId);
    // Custom method to delete answers by questionId
    @Modifying
    @Transactional
    @Query("DELETE FROM Answer a WHERE a.question.questionId = :questionId")
    void deleteByQuestionId(@Param("questionId") Long questionId);


    @Query("SELECT a FROM Answer a WHERE a.author.id = :userId")
    List<Answer> findByUserId(@Param("userId") Long userId);
}


