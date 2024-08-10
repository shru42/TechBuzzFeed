package com.example.TechBuzzFeed.services.Impl;

import com.example.TechBuzzFeed.entity.Question;
import com.example.TechBuzzFeed.payloads.QuestionDto;
import com.example.TechBuzzFeed.payloads.TagDto;
import com.example.TechBuzzFeed.repository.QuestionRepository;
import com.example.TechBuzzFeed.repository.TagRepository;
import com.example.TechBuzzFeed.services.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository  tagRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public TagDto createTag(TagDto tagDto) {
//        Question question=this.modelMapper.map()

        return null;
    }

    @Override
    public TagDto getTagById(Long id) {
        return null;
    }
}
