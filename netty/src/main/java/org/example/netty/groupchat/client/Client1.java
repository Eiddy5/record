package org.example.netty.groupchat.client;

public class Client1 {
    public static void main(String[] args) throws Exception {
        new GroupChatClient("127.0.0.1", 7788).run();
    }
}
