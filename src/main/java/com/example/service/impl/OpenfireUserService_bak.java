package com.example.service.impl;

import com.alibaba.fastjson2.JSONArray;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OpenfireUserService_bak {
    private final OkHttpClient client;
    private final String baseUrl;
    private final String authToken;

    public OpenfireUserService_bak(String serverUrl, String authToken) {
        this.client = new OkHttpClient();
        this.baseUrl = serverUrl + "/plugins/restapi/v1";
        this.authToken = authToken;
    }

    /**
     * 获取所有用户列表
     */
    public JSONArray getAllUsers() throws IOException {
        Request request = buildRequest("/users");
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return new JSONArray(response.body().string());
        }
    }

    /**
     * 根据用户名搜索用户
     */
    public JSONArray searchUsers(String keyword) throws IOException {
        Request request = buildRequest("/users?search=" + keyword);
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return new JSONArray(response.body().string());
        }
    }

    /**
     * 构建带认证的HTTP请求
     */
    private Request buildRequest(String path) {
        return new Request.Builder()
                .url(baseUrl + path)
                .header("Accept", "application/json")
                .header("Authorization", authToken)
                .build();
    }

    public static void main(String[] args) {
        String serverUrl = "http://192.168.64.128:9090";
        String authToken = "aSngKTvTU7GnPtXs"; // 替换为实际令牌

        OpenfireUserService_bak service = new OpenfireUserService_bak(serverUrl, authToken);

        try {
            // 获取所有用户
            JSONArray allUsers = service.getAllUsers();
            System.out.println("所有用户数量: " + allUsers.toJSONString());

            // 搜索用户
            JSONArray searchResult = service.searchUsers("wk");
            System.out.println("搜索结果: " + searchResult.toJSONString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
