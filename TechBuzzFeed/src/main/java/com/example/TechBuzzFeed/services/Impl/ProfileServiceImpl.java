package com.example.TechBuzzFeed.services.Impl;

import com.example.TechBuzzFeed.entity.Answer;
import com.example.TechBuzzFeed.entity.Profile;
import com.example.TechBuzzFeed.entity.Question;
import com.example.TechBuzzFeed.entity.User;
import com.example.TechBuzzFeed.exception.ResourceNotFoundException;
import com.example.TechBuzzFeed.payloads.ProfileDto;
import com.example.TechBuzzFeed.repository.AnswerRepository;
import com.example.TechBuzzFeed.repository.ProfileRepository;
import com.example.TechBuzzFeed.repository.QuestionRepository;
import com.example.TechBuzzFeed.repository.UserRepository;
import com.example.TechBuzzFeed.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;



    @Override
    public ProfileDto getUserprofile(Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));

        List<Question> questions = this.questionRepository.findByUserId(userId);
        List<Answer> answers = this.answerRepository.findByUserId(userId);

        ProfileDto profileDTO = new ProfileDto();
        profileDTO.setUserId(user.getUser_id());
        profileDTO.setUsername(user.getName());
        profileDTO.setEmail(user.getEmail());

        // Check if the user's profile is null
        Profile userProfile = user.getProfile();
        if (userProfile != null) {
            profileDTO.setBio(userProfile.getBio() != null ? userProfile.getBio() : "No Bio Available");
            profileDTO.setProfilePictureUrl(userProfile.getProfilePictureUrl());
            profileDTO.setLastActiveDate(userProfile.getLastActiveDate());
        } else {
            // Handle the case where the profile is null
            profileDTO.setBio("No Bio Available");
            profileDTO.setProfilePictureUrl(null);
            profileDTO.setLastActiveDate(null);
        }

        profileDTO.setQuestions(questions);
        profileDTO.setAnswers(answers);

        return profileDTO;
    }

    @Override
    public ProfileDto createOrUpdateProfile(Long userId, ProfileDto profileData) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Profile profile = user.getProfile();
        if (profile == null) {
            profile = new Profile();
            profile.setUser(user);
        }
        profile.setBio(profileData.getBio());
        profile.setProfilePictureUrl(profileData.getProfilePictureUrl());
        profile.setLastActiveDate(profileData.getLastActiveDate());

        profileRepository.save(profile);

        // Create and return updated ProfileDto
        // Ensure getters and setters are correct
        ProfileDto updatedProfileDto = new ProfileDto();
        updatedProfileDto.setUserId(user.getUser_id());
        updatedProfileDto.setUsername(user.getName());
        updatedProfileDto.setEmail(user.getEmail());
        updatedProfileDto.setBio(profile.getBio());
        updatedProfileDto.setProfilePictureUrl(profile.getProfilePictureUrl());
        updatedProfileDto.setLastActiveDate(profile.getLastActiveDate());
        updatedProfileDto.setQuestions(user.getQuestions().stream().toList());
        updatedProfileDto.setAnswers(user.getAnswers().stream().toList());

        return updatedProfileDto;
    }

    @Override
    public ProfileDto deleteProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Profile profile = user.getProfile();
        if (profile != null) {
            profileRepository.delete(profile);
        }

        userRepository.delete(user);

        // Return the deleted profile information if needed
        ProfileDto deletedProfileDto = new ProfileDto();
        deletedProfileDto.setUserId(user.getUser_id());
        deletedProfileDto.setUsername(user.getName());
        deletedProfileDto.setEmail(user.getEmail());
        deletedProfileDto.setBio(null);
        deletedProfileDto.setProfilePictureUrl(null);
        deletedProfileDto.setLastActiveDate(null);
        deletedProfileDto.setQuestions(user.getQuestions().stream().toList());
        deletedProfileDto.setAnswers(user.getAnswers().stream().toList());

        return deletedProfileDto;    }
}
