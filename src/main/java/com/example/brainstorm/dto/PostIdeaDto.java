package com.example.brainstorm.dto;

import org.apache.commons.codec.net.URLCodec;

public class PostIdeaDto {
	private String id;
	private String key;
	private String userId;
	private String ideaText;

	public void urlEncode() {
		URLCodec codec = new URLCodec("UTF-8");
		try {
			ideaText = codec.encode(ideaText, "UTF-8");
		} catch (Exception e) {
			System.out.println("Exception at SearchIdeaDto.urlDecode. e = " + e.getMessage());
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

	public String getIdeaText() {
		return ideaText;
	}

	public void setIdeaText(String ideaText) {
		this.ideaText = ideaText;
	}
}

