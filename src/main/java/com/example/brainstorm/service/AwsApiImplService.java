package com.example.brainstorm.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

import com.example.brainstorm.dao.AwsApiDao;
import com.example.brainstorm.dto.PostIdeaDto;
import com.example.brainstorm.dto.ResponseSearchResultDto;
import com.example.brainstorm.dto.SearchIdeaDto;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AwsApiImplService implements AwsApiService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AwsApiDao awsApiDao;

    private String postIdea(String request,String userId) {
        logger.info("[IN] AwsApiImplService.postIdea");
        PostIdeaDto postIdea = new PostIdeaDto();
        postIdea.setIdeaText(request);
        postIdea.setUserId(userId);
        String response = awsApiDao.postIdea(postIdea);
        String ret = "いいアイデアだね！メモしておこう。";
        logger.info("[OUT] AwsApiImplService.postIdea");
        return ret;
    }

    private String sendQuery(String request,String userId) {
        logger.info("[IN] AwsApiImplService.sendQuery");
        SearchIdeaDto searchIdeaDto = new SearchIdeaDto();
        searchIdeaDto.setUserId(userId);
        searchIdeaDto.setKeyword(request);
        //ResponseSearchResultDto responseSearchResultDto = awsApiDao.searchIdea(searchIdeaDto);
        String response = awsApiDao.searchIdea(searchIdeaDto);
        if (response.isEmpty()) {
            logger.info("[OUT] AwsApiImplService.sendQuery(Empty)");
            return request + "に関連するアイデアは出してないよ。";
        } else {
            logger.info("[OUT] AwsApiImplService.sendQuery response ->" + response);
            return response;
        }
    }

    private String huun() {
        logger.info("[IN] AwsApiImplService.huun");
        Random random = new Random();
        int randomValue = random.nextInt(3);
        String ret = "";
        if (Objects.equals(randomValue,0)) {
            ret = "何の話〜? 教えてー";
        } else if (Objects.equals(randomValue,1)) {
            ret = "興味津々";
        } else if (Objects.equals(randomValue,2)) {
            ret = "ほほぅ";
        }
        logger.info("[OUT] AwsApiImplService.huun");
        return ret;
    }

    public static String getIp() throws Exception {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = "grobal ip = " + in.readLine();
            return ip;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Message selectApi(String request,String userId) {
        logger.info("[IN] AwsApiImplService.selectApi");
        String idea_mark = "!";
        String query_mark = "?";
        String request_ip = "IP please xxxxx!";
        if (request.isEmpty()) {
            final String ret = "request is null!";
            return new TextMessage(ret);
        } else if (request.startsWith(idea_mark)) {
            final String ret = this.postIdea(request.substring(1),userId);
            logger.info("[OUT] AwsApiImplService.selectApi -> postIdea");
            return new TextMessage(ret);
        } else if (request.startsWith(query_mark)) {
            final String ret = this.sendQuery(request.substring(1),userId);
            logger.info("[OUT] AwsApiImplService.selectApi -> sendQuery");
            return new TextMessage(ret);
        } else if (request.startsWith(request_ip)) {
            String ip_str = "";
            try {
                ip_str = this.getIp();
            } catch (Exception e) {
                ip_str = "ip get err";
            }
            final String ret = ip_str;
            logger.info("[OUT] AwsApiImplService.selectApi -> ip");
            return new TextMessage(ret);
        } else {
            final String ret = this.huun();
            logger.info("[OUT] AwsApiImplService.selectApi -> huun");
            return new TextMessage(ret);
        }
    }
}

