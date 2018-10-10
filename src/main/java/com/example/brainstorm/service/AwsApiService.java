package com.example.brainstorm.service;

import com.linecorp.bot.model.message.Message;

public interface AwsApiService {
    Message selectApi(String request,String userId);
}

