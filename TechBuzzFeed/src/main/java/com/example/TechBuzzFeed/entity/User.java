package com.example.TechBuzzFeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;
    @Column(name = "user_name", nullable = false,length = 100)
    private String name;
    private String email;
    private String password;
    @OneToMany(mappedBy = "author")
    private Set<Question> questions;
    @OneToMany(mappedBy = "author")
    private Set<Answer> answers;



    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Profile profile;

    @OneToMany(mappedBy = "author")
    private Set<Vote> votes;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


}
