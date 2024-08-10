package com.example.TechBuzzFeed.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private Long user_id;
    private String name;
    private String email;
    private String password;
    private Set<String> roles;

}
