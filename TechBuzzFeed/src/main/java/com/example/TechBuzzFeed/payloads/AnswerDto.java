package com.example.TechBuzzFeed.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerDto {
    private  Long answerId;
    private String content;
    private Long questionId;
    private Long userId;
}
