package com.example.brainstorm.dto;

import java.util.List;

public class ResponseSearchResultDto {
    private String status;
    private List<IdeaDto> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<IdeaDto> getResult() {
        return result;
    }

    public void setResult(List<IdeaDto> result) {
        this.result = result;
    }
}
