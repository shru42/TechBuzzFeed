package com.example.TechBuzzFeed.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "answer")
@NoArgsConstructor
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnore
    private User author;
    @ManyToOne
    @JoinColumn(name = "question_id",nullable = false)
    @JsonBackReference
    private Question question;
    @OneToMany(mappedBy = "answer")
    private List<Vote> votes;

    private int upvoteCount;
    private int downvoteCount;
}
