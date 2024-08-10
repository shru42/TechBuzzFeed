package com.example.TechBuzzFeed.controller;

import com.example.TechBuzzFeed.payloads.AnswerDto;
import com.example.TechBuzzFeed.payloads.ApiResponse;
import com.example.TechBuzzFeed.services.AnswerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerServices answerService;
    @PostMapping("/{questionId}/{userId}")
    public ResponseEntity<AnswerDto> createAnswer(@RequestBody AnswerDto answerDto,
                                                  @PathVariable Long questionId,
                                                  @PathVariable Long userId) {
        AnswerDto createdAnswer = this.answerService.createAnswer(answerDto, questionId, userId);
        return new ResponseEntity<>(createdAnswer, HttpStatus.CREATED);
    }

    @PutMapping("/{answerId}")
    public ResponseEntity<AnswerDto> updateAnswer(@PathVariable Long answerId,
                                                  @RequestBody AnswerDto answerDto) {
        AnswerDto updatedAnswer = this.answerService.updateAnswer(answerId, answerDto);
        return new ResponseEntity<>(updatedAnswer, HttpStatus.OK);
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<ApiResponse> deleteAnswer(@PathVariable Long answerId) {
        this.answerService.deleteAnswer(answerId);
        return new ResponseEntity(new ApiResponse("answer deleted successfully",true),(HttpStatus.NO_CONTENT));
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<AnswerDto>> getAnswersByQuestion(@PathVariable Long questionId) {
        List<AnswerDto> answers = this.answerService.getAnswersByQuestion(questionId);
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }
}
