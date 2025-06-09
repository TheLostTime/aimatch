package com.example.entity;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;

public class OpenfireClient {
    private static final String SERVER = "192.168.64.128";
    private static final int PORT = 5222;
    private static final String SERVICE_NAME = "your-service-name";

    private XMPPTCPConnection connection;

    public static void main(String[] args) {
        OpenfireClient client = new OpenfireClient();
        try {
            client.connect();
            client.login("wk", "wk");
            client.sendMessage("recipient@your-service-name", "Hello from Openfire client!");
            client.disconnect();
        } catch (XMPPException | IOException | SmackException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws XMPPException, IOException, SmackException, InterruptedException {
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setXmppDomain(SERVICE_NAME)
                .setHost(SERVER)
                .setPort(PORT)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .build();

        connection = new XMPPTCPConnection(config);
        connection.connect();
        System.out.println("Connected to server: " + SERVER);
    }

    public void login(String username, String password) throws SmackException, IOException, XMPPException, InterruptedException {
        connection.login(username, password);
        System.out.println("Logged in as: " + username);
    }

    public void sendMessage(String recipient, String message) throws XmppStringprepException, InterruptedException, SmackException.NotConnectedException {
        EntityBareJid jid = JidCreate.entityBareFrom(recipient);
        ChatManager chatManager = ChatManager.getInstanceFor(connection);
        Chat chat = chatManager.chatWith(jid);

        // 正确使用 MessageBuilder 并发送消息
        chat.send(message);

        System.out.println("Message sent to: " + recipient);
    }

    public void disconnect() {
        if (connection != null && connection.isConnected()) {
            connection.disconnect();
            System.out.println("Disconnected from server");
        }
    }
}