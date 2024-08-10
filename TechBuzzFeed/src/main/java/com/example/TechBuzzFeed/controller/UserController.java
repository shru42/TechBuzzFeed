package com.example.TechBuzzFeed.controller;
import com.example.TechBuzzFeed.payloads.ApiResponse;
import com.example.TechBuzzFeed.payloads.UserDto;
import com.example.TechBuzzFeed.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/users")
@RestController
public class UserController {
    @Autowired
    private UserServices userServices;
    @PostMapping("/")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        UserDto register=this.userServices.registerUser(userDto);
        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Long id){
        UserDto updated=this.userServices.updateUser(userDto,id);
        return  ResponseEntity.ok(updated);
    }
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(this.userServices.getAllUsers());
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id ){
        return ResponseEntity.ok(this.userServices.getUserById(id));
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<UserDto> getByname(@PathVariable String name ){
       UserDto userDto=this.userServices.getUserByUsername(name);
        return ResponseEntity.ok(userDto);
    }

@DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id){
        this.userServices.deleteUser(id);
        return new ResponseEntity(new ApiResponse("user deleted successfully",true),HttpStatus.OK);
    }
}
