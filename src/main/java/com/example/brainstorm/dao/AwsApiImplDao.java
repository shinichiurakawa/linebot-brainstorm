package com.example.brainstorm.dao;


import com.example.brainstorm.config.AwsConnection;
import com.example.brainstorm.dto.PostIdeaDto;
import com.example.brainstorm.dto.ResponseSearchResultDto;
import com.example.brainstorm.dto.SearchIdeaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Component
public class AwsApiImplDao implements AwsApiDao {
    private static final Logger logger = LoggerFactory.getLogger(AwsApiImplDao.class);
    @Autowired
    AwsConnection awsConnection;


    private String createIdeaPostString() {
        String end_point = awsConnection.getHostname() + ":" + awsConnection.getPort() + awsConnection.getIdeaPostEndpoint();
        return end_point;
    }

    private String createIdeaSearchString() {
        String end_point = awsConnection.getHostname() + ":" + awsConnection.getPort() + awsConnection.getIdeaSearchEndpoint();
        return end_point;
    }

    public CloseableHttpClient createOrgHttpClient() throws  Exception {
        logger.info("[IN] AwsApiDao.createOrgHttpClient");
        if (awsConnection.checkCertification()) {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            return httpclient;
        }

        try {
            TrustStrategy trustStrategy = new TrustSelfSignedStrategy();
            SSLContext sslContext =
                    SSLContexts
                            .custom()
                            .loadTrustMaterial(trustStrategy)
                            .build();
            //
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            CloseableHttpClient client =
                    HttpClients
                            .custom()
                            .setSSLContext(sslContext)
                            .setSSLHostnameVerifier(hostnameVerifier)
                            .build();
            logger.info("[OUT] AwsApiDao.createOrgHttpClient");
            return client;
        } catch (Exception e) {
            logger.info("[Exception] AwsApiDao.createOrgHttpClient e = " + e.getMessage());
            throw e;
        }
    }

    public String postIdea(PostIdeaDto ideaDto) {
        logger.info("[IN] AwsApiDao.postIdea");

        String ret = "";
        CloseableHttpClient httpclient = null;
        HttpPost request = new HttpPost(this.createIdeaPostString());
        ObjectMapper mapper = new ObjectMapper();

        ideaDto.setId(awsConnection.getId());
        ideaDto.setKey(awsConnection.getKey());
        ideaDto.urlEncode();

        CloseableHttpResponse response = null;

        // header
        request.addHeader("Content-type","application/json; charset=UTF-8");

        try {
            httpclient = createOrgHttpClient();
            String json = mapper.writeValueAsString(ideaDto);
            request.setEntity(new StringEntity(json));

            response = httpclient.execute(request);
            logger.debug("json : " + json);

            int status = response.getStatusLine().getStatusCode();

            if (status == HttpStatus.SC_OK){
                ret = EntityUtils.toString(response.getEntity(),StandardCharsets.UTF_8);
            }
        } catch (ClientProtocolException e) {
            logger.info("ClientProtocolException: " + e.getStackTrace());
            ret = "system error.";
        } catch (UnsupportedEncodingException e) {
            logger.info("UnsupportedEncodingException: " + e.getStackTrace());
            ret = "system error.";
        } catch (IOException e) {
            logger.info("IOException: " + e.getStackTrace());
            ret = "system error.";
        } catch (Exception e) {
            logger.info("Exception: " + e.getStackTrace());
            ret = "system error.";
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpclient != null) {
                    httpclient.close();
                }
            } catch (IOException e) {
                logger.info("Exception: " + e.getStackTrace());
                ret = "system error.";
            }
        }
        logger.info("[OUT] AwsApiDao.postIdea" + ret);
        return ret;
    }

    //public ResponseSearchResultDto searchIdea(SearchIdeaDto ideaDto) {
    public String searchIdea(SearchIdeaDto ideaDto) {
        logger.info("[IN] AwsApiDao.searchIdea");
        ResponseSearchResultDto responseSearchResultDto = null;
        String ret = "";
        CloseableHttpClient httpclient = null;
        HttpPost request = new HttpPost(this.createIdeaSearchString());
        ObjectMapper mapper = new ObjectMapper();

        ideaDto.setId(awsConnection.getId());
        ideaDto.setKey(awsConnection.getKey());
        ideaDto.urlEncode();

        CloseableHttpResponse response = null;

        // header
        request.addHeader("Content-type","application/json; charset=UTF-8");

        try {
            httpclient = createOrgHttpClient();
            String json = mapper.writeValueAsString(ideaDto);
            request.setEntity(new StringEntity(json));

            response = httpclient.execute(request);
            logger.debug("json : " + json);

            int status = response.getStatusLine().getStatusCode();

            if (status == HttpStatus.SC_OK){
                logger.info("[--] AwsApiDao.searchIdea : status == HttpStatus.SC_OK(1)");
                ret = EntityUtils.toString(response.getEntity(),StandardCharsets.UTF_8);
                //responseSearchResultDto = mapper.readValue(EntityUtils.toString(response.getEntity(),StandardCharsets.UTF_8),ResponseSearchResultDto.class);
                logger.info("[--] AwsApiDao.searchIdea : status == HttpStatus.SC_OK(2) ret = " + ret);
            } else {
                logger.info("[--] AwsApiDao.searchIdea : status != HttpStatus.SC_OK");
            }
        } catch (ClientProtocolException e) {
            logger.info("ClientProtocolException: " + e.getStackTrace());
            responseSearchResultDto.setStatus("NG");
        } catch (UnsupportedEncodingException e) {
            logger.info("UnsupportedEncodingException: " + e.getStackTrace());
            responseSearchResultDto.setStatus("NG");
        } catch (IOException e) {
            logger.info("IOException: " + e.getStackTrace());
            responseSearchResultDto.setStatus("NG");
        } catch (Exception e) {
            logger.info("Exception: " + e.getStackTrace());
            responseSearchResultDto.setStatus("NG");
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpclient != null) {
                    httpclient.close();
                }
            } catch (IOException e) {
                logger.info("IOException: " + e.getStackTrace());
                responseSearchResultDto.setStatus("NG");
            }
        }
        logger.info("[OUT] AwsApiDao.searchIdea" + ret);
        return ret;
        //return responseSearchResultDto;
    }

}

