package com.example.TechBuzzFeed.services;

import com.example.TechBuzzFeed.payloads.AnswerDto;

import java.util.List;

public interface AnswerServices {
    AnswerDto createAnswer(AnswerDto answerDto, Long questionId, Long userId);
    AnswerDto updateAnswer(Long answerId, AnswerDto answerDto);
    void deleteAnswer(Long answerId);
    List<AnswerDto> getAnswersByQuestion(Long questionId);
}

