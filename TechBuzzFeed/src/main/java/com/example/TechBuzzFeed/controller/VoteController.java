package com.example.TechBuzzFeed.controller;

import com.example.TechBuzzFeed.entity.User;
import com.example.TechBuzzFeed.payloads.UserDto;
import com.example.TechBuzzFeed.services.UserServices;
import com.example.TechBuzzFeed.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping("/answer/{answerId}")
    public void vote(@PathVariable Long answerId,
                     @RequestParam boolean upvote,
                     @RequestBody UserDto userDto) {
        voteService.vote(userDto, answerId, upvote);
    }
}

