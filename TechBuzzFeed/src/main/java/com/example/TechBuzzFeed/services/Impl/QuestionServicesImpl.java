package com.example.TechBuzzFeed.services.Impl;

import com.example.TechBuzzFeed.entity.Question;
import com.example.TechBuzzFeed.entity.Tag;
import com.example.TechBuzzFeed.entity.User;
import com.example.TechBuzzFeed.exception.ResourceNotFoundException;
import com.example.TechBuzzFeed.payloads.QuestionDto;
import com.example.TechBuzzFeed.payloads.QuestionResponse;
import com.example.TechBuzzFeed.repository.AnswerRepository;
import com.example.TechBuzzFeed.repository.QuestionRepository;
import com.example.TechBuzzFeed.repository.TagRepository;
import com.example.TechBuzzFeed.repository.UserRepository;
import com.example.TechBuzzFeed.services.QuestionServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionServicesImpl implements QuestionServices {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private TagRepository tagRepository;
    @Override
    public QuestionDto createQuestion(QuestionDto questionDto,Long userId) {
        // Fetch the user by ID
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId, "First login then you can post a question"));

        // Map DTO to Entity
        Question question = this.modelMapper.map(questionDto, Question.class);
        question.setAuthor(user);  // Set the User entity as the author
        question.setAddedDate(new Date());

        // Handle tags as strings
        if (questionDto.getTags() != null && !questionDto.getTags().isEmpty()) {
            Set<Tag> tags = new HashSet<>();
            for (String tagName : questionDto.getTags()) {
                Tag tag = this.tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagName);
                            return this.tagRepository.save(newTag);  // Save the new tag if not found
                        });
                tags.add(tag);
            }
            question.setTags(tags);  // Set the tags for the question
        }

        // Save the question
        Question newQuestion = this.questionRepository.save(question);

        // Map the saved question back to DTO
        QuestionDto savedQuestionDto = this.modelMapper.map(newQuestion, QuestionDto.class);
        savedQuestionDto.setUserId(userId);  // Set the userId in the DTO

        // Populate tag names in DTO
        if (newQuestion.getTags() != null && !newQuestion.getTags().isEmpty()) {
            Set<String> tagNames = newQuestion.getTags().stream()
                    .map(Tag::getName)  // Convert each Tag entity to its name
                    .collect(Collectors.toSet());  // Collect the names into a Set
            savedQuestionDto.setTags(tagNames);  // Set the tags in the DTO
        }

        return savedQuestionDto;
    }

    @Override
    public QuestionDto getQuestionById(Long id) {
        Question question = this.questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", id));

        // Convert the Question entity to a QuestionDto
        QuestionDto questionDto = this.modelMapper.map(question, QuestionDto.class);

        // Populate tag names from the Tag entities
        if (question.getTags() != null && !question.getTags().isEmpty()) {
            Set<String> tagNames = question.getTags().stream()
                    .map(Tag::getName)  // Convert each Tag entity to its name
                    .collect(Collectors.toSet());  // Collect the names into a Set
            questionDto.setTags(tagNames);
        }
        return questionDto;
    }

    @Override
    public QuestionResponse getAllQuestions(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        // Determine the sorting order (ascending/descending)
        Sort sort = (sortDir.equalsIgnoreCase("asc") )? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // Create a pageable object with the specified page number, size, and sort order
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Fetch the paginated and sorted list of questions from the repository
        Page<Question> pageQuestions = questionRepository.findAll(pageable);

        // Convert the list of Question entities to a list of QuestionDto
        List<QuestionDto> questionDtos = pageQuestions.getContent().stream()
                .map(question -> {
                    // Convert Question entity to QuestionDto
                    QuestionDto questionDto = modelMapper.map(question, QuestionDto.class);

                    // Map the tags to their names for the DTO
                    if (question.getTags() != null && !question.getTags().isEmpty()) {
                        Set<String> tagNames = question.getTags().stream()
                                .map(Tag::getName)  // Convert each Tag entity to its name
                                .collect(Collectors.toSet());  // Collect the names into a Set
                        questionDto.setTags(tagNames);  // Set the tags in the DTO
                    }

                    return questionDto;
                })
                .collect(Collectors.toList());

        // Create and populate the QuestionResponse object
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setContent(questionDtos);
        questionResponse.setPageNumber(pageQuestions.getNumber());
        questionResponse.setPageSize(pageQuestions.getSize());
        questionResponse.setTotalElements(pageQuestions.getTotalElements());
        questionResponse.setTotalPages(pageQuestions.getTotalPages());
        questionResponse.setLastPage(pageQuestions.isLast());

        // Return the populated QuestionResponse object
        return  questionResponse;
    }


    @Override
    public QuestionDto updateQuestion(Long questionId, QuestionDto questionDto) {
        Question existingQuestion = this.questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));

        // Update the existing question with new details
        existingQuestion.setTitle(questionDto.getTitle());
        existingQuestion.setContent(questionDto.getContent());

        // Save the updated question
        Question updatedQuestion = this.questionRepository.save(existingQuestion);

        // Convert the updated question to QuestionDto
        return this.modelMapper.map(updatedQuestion, QuestionDto.class);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        Question existingQuestion = this.questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "id", questionId));
        //delete all answers associated to that question
        this.answerRepository.deleteByQuestionId(questionId);
        // Delete the question
        this.questionRepository.delete(existingQuestion);
    }

    @Override
    public List<QuestionDto> getQuestionsByUser(Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        // Fetch all questions by user ID
        List<Question> questions = questionRepository.findByUserId(userId);
        if(questions.isEmpty())
            throw  new ResourceNotFoundException("Question","userId",userId,"Post questions First");
        // Convert the list of Question entities to a list of QuestionDto
        return questions.stream()
                .map(question -> {
                    QuestionDto questionDto = modelMapper.map(question, QuestionDto.class);

                    // Convert tags to their names
                    if (question.getTags() != null && !question.getTags().isEmpty()) {
                        Set<String> tagNames = question.getTags().stream()
                                .map(Tag::getName)
                                .collect(Collectors.toSet());
                        questionDto.setTags(tagNames);
                    }

                    return questionDto;
                })
                .collect(Collectors.toList());
    }



    @Override
    public List<QuestionDto> getQuestionsByTag(String tag) {
        List<Question> questions=this.questionRepository.findByTagName(tag);
        List<QuestionDto> questionDtos=questions.stream().map(question -> {
            QuestionDto questionDto=modelMapper.map(question,QuestionDto.class);
            if (question.getTags() != null && !question.getTags().isEmpty()) {
                Set<String> tagNames = question.getTags().stream()
                        .map(Tag::getName)
                        .collect(Collectors.toSet());
                questionDto.setTags(tagNames);
            }

            return questionDto;
        }).collect(Collectors.toList());
        return questionDtos;
    }

    @Override
    public List<QuestionDto> searchQuestions(String keyword) {
        List<Question> questions = questionRepository.searchByKeyword(keyword);
        return questions.stream()
                .map(question -> modelMapper.map(question, QuestionDto.class))
                .collect(Collectors.toList());

    }

}
