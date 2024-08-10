package com.example.TechBuzzFeed.services.Impl;

import com.example.TechBuzzFeed.entity.Role;
import com.example.TechBuzzFeed.entity.User;
import com.example.TechBuzzFeed.exception.ResourceNotFoundException;
import com.example.TechBuzzFeed.payloads.UserDto;
import com.example.TechBuzzFeed.repository.RoleRepository;
import com.example.TechBuzzFeed.repository.UserRepository;
import com.example.TechBuzzFeed.services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleRepository roleRepository;




    @Override
    public UserDto registerUser(UserDto userDto) {
        User user=this.dtoToUser(userDto);
        User savedUser = userRepository.save(user);
        return userToDto(savedUser);
    }

    @Override
    public UserDto authenticateUser(String username, String password) {
        return null;
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user= this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        return this.userToDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDTO,Long userId) {
        User user= this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        User updated=this.userRepository.save(user);
        return this.userToDto(user);
    }

    @Override
    public void deleteUser(Long userId) {

       User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
       this.userRepository.deleteById(userId);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users=this.userRepository.findAll();
        List<UserDto> userDtos=users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void changePassword(Long userId, String newPassword) {

    }

    @Override
    public void resetPassword(String email) {

    }

    @Override
    public void assignRoleToUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","id",userId));

        // Fetch the role by roleName
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("role","id","roleName"));

        user.setRole(role);

        // Save the updated user back to the repository
        userRepository.save(user);
    }

    @Override
    public void removeRoleFromUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("role","id","roleName"));

        // Fetch the role by roleName
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("role","id","roleName"));
        user.setRole(null);

        // Save the updated user back to the repository
        userRepository.save(user);

    }

    @Override
    public void sendNotification(Long userId, String message) {

    }

    @Override
    public List<UserDto> searchUsers(String keyword) {

        return null;
    }
    public UserDto userToDto(User user){
        UserDto userDto= this.modelMapper.map(user, UserDto.class);
        return userDto;
    }
    public User dtoToUser(UserDto userDto){
        User user= this.modelMapper.map(userDto, User.class);
        return user;
    }
}
