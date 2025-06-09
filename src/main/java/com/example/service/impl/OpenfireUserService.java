package com.example.service.impl;

import org.igniterealtime.restclient.RestApiClient;
import org.igniterealtime.restclient.entity.AuthenticationToken;
import org.igniterealtime.restclient.entity.UserEntities;
import org.igniterealtime.restclient.enums.SupportedMediaType;
import org.springframework.web.client.RestClientException;

public class OpenfireUserService {

    public static void main(String[] args) {
        String serverUrl = "http://192.168.64.128";
        String apiKey = "aSngKTvTU7GnPtXs"; // 替换为实际API密钥
        RestApiClient client = new RestApiClient(
                serverUrl,9090, new AuthenticationToken(apiKey), SupportedMediaType.JSON);
        try {
            // 获取所有用户
            UserEntities allUsers = client.getUsers();
            System.out.println("所有用户: " + allUsers.getUsers().size());

        } catch (RestClientException e) {
            e.printStackTrace();
        }
    }
}
