package com.example.TechBuzzFeed.services;

import com.example.TechBuzzFeed.payloads.TagDto;

public interface TagService {
    TagDto createTag(TagDto tagDto);
    TagDto getTagById(Long id);
}
