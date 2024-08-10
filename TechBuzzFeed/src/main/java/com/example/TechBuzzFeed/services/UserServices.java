package com.example.TechBuzzFeed.services;

import com.example.TechBuzzFeed.entity.User;
import com.example.TechBuzzFeed.payloads.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserServices {

    //registration
    UserDto registerUser(UserDto userDto);

    // User Authentication
    UserDto authenticateUser(String username, String password);

    // User Profile Management
    UserDto getUserById(Long userId);
    UserDto updateUser(UserDto userDTO, Long userId);
    void deleteUser(Long userId);

    // User Details
    UserDto getUserByUsername(String username);
    List<UserDto> getAllUsers();

    // Password Management
    void changePassword(Long userId, String newPassword);
    void resetPassword(String email);

    // User Role Management
    void assignRoleToUser(Long userId, String roleName);
    void removeRoleFromUser(Long userId, String roleName);

    // User Notifications
    void sendNotification(Long userId, String message);

    // User Search and Filtering
    List<UserDto> searchUsers(String keyword);
}
