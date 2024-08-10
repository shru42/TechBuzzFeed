package com.example.TechBuzzFeed.services.Impl;

import com.example.TechBuzzFeed.entity.Answer;
import com.example.TechBuzzFeed.entity.Question;
import com.example.TechBuzzFeed.entity.User;
import com.example.TechBuzzFeed.exception.ResourceNotFoundException;
import com.example.TechBuzzFeed.payloads.AnswerDto;
import com.example.TechBuzzFeed.repository.AnswerRepository;
import com.example.TechBuzzFeed.repository.QuestionRepository;
import com.example.TechBuzzFeed.repository.UserRepository;
import com.example.TechBuzzFeed.services.AnswerServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerServices {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public AnswerDto createAnswer(AnswerDto answerDto, Long questionId, Long userId) {
        Question question = this.questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Answer answer = this.modelMapper.map(answerDto, Answer.class);
        answer.setQuestion(question);
        answer.setAuthor(user);

        Answer savedAnswer = this.answerRepository.save(answer);
        return this.modelMapper.map(savedAnswer, AnswerDto.class);    }

    @Override
    public AnswerDto updateAnswer(Long answerId, AnswerDto answerDto) {
        Answer answer = this.answerRepository.findById(answerId)
                .orElseThrow(() -> new ResourceNotFoundException("Answer", "id", answerId));

        answer.setContent(answerDto.getContent());
        Answer updatedAnswer = this.answerRepository.save(answer);
        return this.modelMapper.map(updatedAnswer, AnswerDto.class);
    }

    @Override
    public void deleteAnswer(Long answerId) {
        Answer answer = this.answerRepository.findById(answerId)
                .orElseThrow(() -> new ResourceNotFoundException("Answer", "id", answerId));
        this.answerRepository.delete(answer);
    }

    @Override
    public List<AnswerDto> getAnswersByQuestion(Long questionId) {
        Question ques=this.questionRepository.findById(questionId).orElseThrow(()->new ResourceNotFoundException("Question","id",questionId));
        List<Answer> answers = this.answerRepository.findByQuestion_QuestionId(questionId);
        return answers.stream()
                .map(answer -> this.modelMapper.map(answer, AnswerDto.class))
                .collect(Collectors.toList());
    }
}
