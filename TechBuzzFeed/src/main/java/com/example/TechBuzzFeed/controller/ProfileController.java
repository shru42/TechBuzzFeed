package com.example.TechBuzzFeed.controller;

import com.example.TechBuzzFeed.payloads.ApiResponse;
import com.example.TechBuzzFeed.payloads.ProfileDto;
import com.example.TechBuzzFeed.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @PutMapping("/{userId}")
    public ResponseEntity<ProfileDto> createOrUpdateProfile(@PathVariable Long userId, @RequestBody ProfileDto userProfile) {
        ProfileDto updatedProfile = this.profileService.createOrUpdateProfile(userId, userProfile);
        return ResponseEntity.ok(updatedProfile);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileDto> getUserProfile(@PathVariable Long userId){
        ProfileDto profileDto=this.profileService.getUserprofile(userId);
        return ResponseEntity.ok(profileDto);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteProfile(@PathVariable Long userId) {
        profileService.deleteProfile(userId);
        return new ResponseEntity<>(new ApiResponse("Profile deleted successfully",true), HttpStatus.OK);
    }
}
