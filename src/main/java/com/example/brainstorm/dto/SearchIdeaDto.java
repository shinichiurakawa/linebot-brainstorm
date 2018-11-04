package com.example.brainstorm.dto;

import org.apache.commons.codec.net.URLCodec;

public class SearchIdeaDto {
    private String id;
    private String key;
    private String userId;
    private String keyword;

    public void urlEncode() {
        URLCodec codec = new URLCodec("UTF-8");
        try {
            keyword = codec.encode(keyword, "UTF-8");
        } catch (Exception e) {
            System.out.println("Exception at SearchIdeaDto.urlEncode. e = " + e.getMessage());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}