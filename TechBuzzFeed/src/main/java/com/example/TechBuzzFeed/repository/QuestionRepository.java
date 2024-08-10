package com.example.TechBuzzFeed.repository;

import com.example.TechBuzzFeed.entity.Question;
import com.example.TechBuzzFeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface QuestionRepository extends JpaRepository<Question, Long> {



    @Query("SELECT q FROM Question q JOIN q.tags t WHERE t.name = :tagName")
    List<Question> findByTagName(@Param("tagName") String tagName);

@Query("SELECT q FROM Question q WHERE q.author.id = :userId")
List<Question> findByUserId(@Param("userId") Long userId);


    @Query("SELECT q FROM Question q WHERE q.title LIKE %:keyword% OR q.content LIKE %:keyword%")
    List<Question> searchByKeyword(@Param("keyword") String keyword);
}
