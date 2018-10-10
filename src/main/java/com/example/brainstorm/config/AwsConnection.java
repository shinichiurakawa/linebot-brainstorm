package com.example.brainstorm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "awsconnection")
public class AwsConnection {
    private String id;
    private String key;
    private String hostname;
    private String ideaSearchEndpoint;
    private String ideaPostEndpoint;
    private String port;

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

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIdeaSearchEndpoint() {
        return ideaSearchEndpoint;
    }

    public void setIdeaSearchEndpoint(String ideaSearchEndpoint) {
        this.ideaSearchEndpoint = ideaSearchEndpoint;
    }

    public String getIdeaPostEndpoint() {
        return ideaPostEndpoint;
    }

    public void setIdeaPostEndpoint(String ideaPostEndpoint) {
        this.ideaPostEndpoint = ideaPostEndpoint;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
