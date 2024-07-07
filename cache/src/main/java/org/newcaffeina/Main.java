package org.newcaffeina;

import org.newcaffeina.message.Action;
import org.newcaffeina.message.Message;

public class Main {

    public static void main(String[] args) {
        Message message = Message.builder()
                .action(Action.ADD)
                .key("key")
                .value("value")
                .build();

        System.out.println(message);
    }
}
