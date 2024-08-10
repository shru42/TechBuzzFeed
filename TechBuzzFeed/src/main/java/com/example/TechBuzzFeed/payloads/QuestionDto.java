package com.example.TechBuzzFeed.payloads;

import com.example.TechBuzzFeed.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class QuestionDto {

    private String title;
    private String content;
    private Long userId;
    private Date addedDate;
    private List<AnswerDto> answers;
    private Set<String> tags;

}
