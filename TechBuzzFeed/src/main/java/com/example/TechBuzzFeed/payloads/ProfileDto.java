package com.example.TechBuzzFeed.payloads;

import com.example.TechBuzzFeed.entity.Answer;
import com.example.TechBuzzFeed.entity.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class ProfileDto {
    private Long userId;
    private String username;
    private String email;
    private String bio;
    private String profilePictureUrl;
    private LocalDateTime lastActiveDate;
    private List<Question> questions;
    private List<Answer> answers;
}
