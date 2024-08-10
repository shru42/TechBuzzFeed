package com.example.TechBuzzFeed.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "question")
@NoArgsConstructor
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long questionId;
   private String title;
   private String content;
   private Date addedDate;
   @ManyToOne
   @JoinColumn(name="user_id",nullable = false)
   @JsonIgnore
   private User author;
   @OneToMany(mappedBy = "question",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JsonBackReference// Prevents recursion by indicating the forward reference
   private Set<Answer>  answers;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "question_tag",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonManagedReference
    private Set<Tag> tags;
//    @OneToMany(mappedBy = "question")
//    private Set<Comment> comments;
//    @OneToMany(mappedBy = "question")
//    private Set<Vote> votes;
//
//    @OneToMany(mappedBy = "question")
//    private Set<Report> reports;
}
