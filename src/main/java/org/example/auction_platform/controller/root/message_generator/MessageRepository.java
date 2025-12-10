package org.example.auction_platform.controller.root.message_generator;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component("messageRepository")
public class MessageRepository {

    @Getter
    private final List<String> messages = List.of(
            "Hello there!",
            "I am alive!",
            "The server is uuuuuuup.",
            "This s&^t works."
    );

    private final Random random = new Random();

    public int getRandomMessageIndex() {
        return random.nextInt(messages.size());
    }
}