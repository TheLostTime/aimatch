package com.example.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpenfireRestClient {
    private final OkHttpClient client;
    private final String apiUrl;
    private final String authHeader;

    public OpenfireRestClient(String serverUrl, String authToken) {
        this.client = new OkHttpClient();
        this.apiUrl = serverUrl + "/plugins/restapi/v1";
        this.authHeader = authToken;
    }

    // 发送消息给指定用户
    public void sendMessage(String fromUser, String toUser, String message) throws IOException {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "chat");
        jsonObject.put("to", toUser);
        jsonObject.put("body", message);

        RequestBody body = RequestBody.create(jsonObject.toJSONString(), JSON);
        Request request = new Request.Builder()
                .url(apiUrl + "/messages/users/" + fromUser)
                .header("Authorization", authHeader)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        }
    }

    // 获取用户的联系人列表（历史发送过消息的所有人）
    public List<String> getContacts(String username) throws IOException {
        Request request = new Request.Builder()
                .url(apiUrl + "/users/" + username + "/contacts")
                .header("Authorization", authHeader)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String jsonData = response.body().string();
            JSONArray jsonArray = new JSONArray(jsonData);

            List<String> contacts = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                contacts.add(jsonArray.getJSONObject(i).getString("jid"));
            }
            return contacts;
        }
    }

    // 获取用户的离线消息数量
    public int getOfflineMessageCount(String username) throws IOException {
        Request request = new Request.Builder()
                .url(apiUrl + "/users/" + username + "/offlinecount")
                .header("Authorization", authHeader)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String jsonData = response.body().string();
            return JSONObject.parseObject(jsonData).getIntValue("count");
        }
    }

    // 获取用户的历史消息
    public List<Message> getHistoryMessages(String username, String withUser, int maxResults) throws IOException {
        Request request = new Request.Builder()
                .url(apiUrl + "/messages/users/" + username + "/history/" + withUser + "?maxResults=" + maxResults)
                .header("Authorization", authHeader)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String jsonData = response.body().string();
            JSONArray jsonArray = new JSONArray(jsonData);

            List<Message> messages = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject msgObj = jsonArray.getJSONObject(i);
                messages.add(new Message(
                        msgObj.getString("from"),
                        msgObj.getString("to"),
                        msgObj.getString("body"),
                        msgObj.getString("sentDate")
                ));
            }
            return messages;
        }
    }

    // 消息数据类
    public static class Message {
        private final String from;
        private final String to;
        private final String body;
        private final String sentDate;

        public Message(String from, String to, String body, String sentDate) {
            this.from = from;
            this.to = to;
            this.body = body;
            this.sentDate = sentDate;
        }

        public String getFrom() { return from; }
        public String getTo() { return to; }
        public String getBody() { return body; }
        public String getSentDate() { return sentDate; }

        @Override
        public String toString() {
            return "[" + sentDate + "] " + from + " -> " + to + ": " + body;
        }
    }

    // 使用示例
    public static void main(String[] args) {
        try {
            OpenfireRestClient client = new OpenfireRestClient(
                    "http://192.168.64.128:9090", "aSngKTvTU7GnPtXs"
            );
            // 发送消息
            client.sendMessage("wk", "wk1", "Hello from API!");

            // 获取联系人
            List<String> contacts = client.getContacts("wk1");
            System.out.println("Contacts: " + contacts);

            // 获取离线消息数量
            int offlineCount = client.getOfflineMessageCount("wk1");
            System.out.println("Offline messages: " + offlineCount);

            // 获取历史消息
            List<Message> history = client.getHistoryMessages("wk1", "wk", 10);
            System.out.println("History messages:");
            history.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
