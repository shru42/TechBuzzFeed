package com.example.TechBuzzFeed.services.Impl;

import com.example.TechBuzzFeed.entity.Answer;
import com.example.TechBuzzFeed.entity.User;
import com.example.TechBuzzFeed.entity.Vote;
import com.example.TechBuzzFeed.exception.ResourceNotFoundException;
import com.example.TechBuzzFeed.payloads.UserDto;
import com.example.TechBuzzFeed.repository.AnswerRepository;
import com.example.TechBuzzFeed.repository.UserRepository;
import com.example.TechBuzzFeed.repository.VoteRepository;
import com.example.TechBuzzFeed.services.VoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public void vote(UserDto userDto, Long answerId, boolean upvote) {
        // Convert UserDto to User entity
        User user = modelMapper.map(userDto, User.class);

        // Fetch the answer entity
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new RuntimeException("Answer not found"));

        // Check if the user has already voted on this answer
        Optional<Vote> existingVote = voteRepository.findByUserAndAnswer(user, answer);

        if (existingVote.isPresent()) {
            // User has already voted, check if the vote type (upvote/downvote) needs to be changed
            Vote vote = existingVote.get();
            if (vote.isUpvote() != upvote) {
                // Change the vote
                if (upvote) {
                    answer.setUpvoteCount(answer.getUpvoteCount() + 1);
                    answer.setDownvoteCount(answer.getDownvoteCount() - 1);
                } else {
                    answer.setUpvoteCount(answer.getUpvoteCount() - 1);
                    answer.setDownvoteCount(answer.getDownvoteCount() + 1);
                }
                vote.setUpvote(upvote);
                voteRepository.save(vote);
            }
        } else {
            // Create a new vote
            Vote vote = new Vote();
            vote.setUser(user);
            vote.setAnswer(answer);
            vote.setUpvote(upvote);
            voteRepository.save(vote);

            if (upvote) {
                answer.setUpvoteCount(answer.getUpvoteCount() + 1);
            } else {
                answer.setDownvoteCount(answer.getDownvoteCount() + 1);
            }
        }

        // Save the updated answer with the new vote counts
        answerRepository.save(answer);
    }
}
