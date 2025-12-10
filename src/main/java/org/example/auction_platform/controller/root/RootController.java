package org.example.auction_platform.controller.root;

import lombok.AllArgsConstructor;
import org.example.auction_platform.controller.root.message_generator.MessageGenerator;
import org.example.auction_platform.controller.root.response.GetRootResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/")
public class RootController {

    private MessageGenerator messageGenerator;

    @GetMapping
    public GetRootResponse getRoot() {
        return GetRootResponse.builder()
                .message(messageGenerator.getMessage())
                .build();
    }
}
