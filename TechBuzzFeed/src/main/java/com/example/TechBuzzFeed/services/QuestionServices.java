package com.example.TechBuzzFeed.services;

import com.example.TechBuzzFeed.payloads.QuestionDto;
import com.example.TechBuzzFeed.payloads.QuestionResponse;

import java.util.List;

public interface QuestionServices {

 QuestionDto createQuestion(QuestionDto questionDto,Long userId);
 QuestionDto getQuestionById(Long questionId);
 QuestionResponse getAllQuestions(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
 QuestionDto updateQuestion(Long questionId, QuestionDto questionDto);
 void deleteQuestion(Long questionId);
 List<QuestionDto> getQuestionsByUser(Long userId);
 List<QuestionDto> getQuestionsByTag(String tag);
 List<QuestionDto> searchQuestions(String keyword);
}
