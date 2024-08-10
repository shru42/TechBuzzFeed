package com.example.TechBuzzFeed.services;

import com.example.TechBuzzFeed.entity.User;
import com.example.TechBuzzFeed.payloads.UserDto;

public interface VoteService {
    void vote(UserDto userDto, Long answerId, boolean upvote);
}
