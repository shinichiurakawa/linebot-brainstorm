package com.example.brainstorm.dao;

import com.example.brainstorm.dto.PostIdeaDto;
import com.example.brainstorm.dto.ResponseSearchResultDto;
import com.example.brainstorm.dto.SearchIdeaDto;

public interface AwsApiDao {
    String postIdea(PostIdeaDto ideaDto);
    //ResponseSearchResultDto searchIdea(SearchIdeaDto searchIdeaDto);
    String searchIdea(SearchIdeaDto searchIdeaDto);
}

