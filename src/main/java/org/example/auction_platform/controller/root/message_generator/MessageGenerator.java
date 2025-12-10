package org.example.auction_platform.controller.root.message_generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Component
@RequestScope
public class MessageGenerator {

    @Value("#{@messageRepository.messages[@messageRepository.getRandomMessageIndex()]}")
    private String customMessage;

    public String getMessage() {
        return customMessage;
    }
}
