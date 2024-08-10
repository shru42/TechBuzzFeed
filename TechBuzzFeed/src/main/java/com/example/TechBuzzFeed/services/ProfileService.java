package com.example.TechBuzzFeed.services;

import com.example.TechBuzzFeed.payloads.ProfileDto;

public interface ProfileService {
    ProfileDto getUserprofile(Long userId);
    ProfileDto createOrUpdateProfile(Long userId, ProfileDto profileData );
    ProfileDto deleteProfile(Long userId);
}
