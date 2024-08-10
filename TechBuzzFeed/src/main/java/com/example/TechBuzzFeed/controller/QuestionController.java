package com.example.TechBuzzFeed.controller;

import com.example.TechBuzzFeed.config.AppConstants;
import com.example.TechBuzzFeed.entity.Question;
import com.example.TechBuzzFeed.payloads.ApiResponse;
import com.example.TechBuzzFeed.payloads.QuestionDto;
import com.example.TechBuzzFeed.payloads.QuestionResponse;
import com.example.TechBuzzFeed.services.QuestionServices;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/qst")
public class QuestionController {
    @Autowired
    private QuestionServices questionServices;

    @PostMapping("/{userId}")
    public ResponseEntity<QuestionDto> createQuestion(@RequestBody QuestionDto questionDto, @PathVariable Long userId) {
     QuestionDto postQues=this.questionServices.createQuestion(questionDto,userId);
     return new ResponseEntity<QuestionDto>(postQues, HttpStatus.CREATED);

    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable Long questionId) {
        QuestionDto questionDto = this.questionServices.getQuestionById(questionId);
        return new ResponseEntity<>(questionDto, HttpStatus.OK);
    }

    //getall questions
    @GetMapping("/all")
    public ResponseEntity<QuestionResponse> getAllQuestions(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        QuestionResponse  questions = this.questionServices.getAllQuestions(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<QuestionResponse>(questions, HttpStatus.OK);
    }
    //updatequestion
    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionDto> updateQuestion(@PathVariable Long questionId, @RequestBody QuestionDto questionDto) {
        QuestionDto updatedQuestion = this.questionServices.updateQuestion(questionId, questionDto);
        return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
    }
    // Delete a question by ID
    @DeleteMapping("/{questionId}")
    public ResponseEntity<ApiResponse> deleteQuestion(@PathVariable Long questionId) {
        try {
            // Call the service to delete the question
            questionServices.deleteQuestion(questionId);
            // Return a success response with status OK
            return new ResponseEntity<>(new ApiResponse("Question deleted successfully", true), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            // Handle case where the question was not found
            return new ResponseEntity<>(new ApiResponse("Question not found", false), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Handle other possible exceptions
            return new ResponseEntity<>(new ApiResponse("An error occurred", false), HttpStatus.INTERNAL_SERVER_ERROR);
        }}
    // Get questions by a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<QuestionDto>> getQuestionsByUser(@PathVariable Long userId) {
        List<QuestionDto> questions = this.questionServices.getQuestionsByUser(userId);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<QuestionDto>> getQuesByTags(@PathVariable String tag){
        List<QuestionDto> questionDtos=this.questionServices.getQuestionsByTag(tag);
        return new ResponseEntity<>(questionDtos,HttpStatus.OK);
    }



}
