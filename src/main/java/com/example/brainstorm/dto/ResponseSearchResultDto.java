package com.example.brainstorm.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseSearchResultDto {
    private String status;
    private List<IdeaDto> result;

    public void urlDecode() {
        result.stream().forEach(idea->{
            idea.urlDecode();
        });
    }
    public String createResponseString() {
        List<String> idea_list = new ArrayList<>();
        for (int idx = 0; idx < result.size(); idx++) {
            result.get(idx).urlDecode();
            idea_list.add(result.get(idx).getIdeaText());
        }
        String ret = String.join(",",idea_list);
        return ret;
    }
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
