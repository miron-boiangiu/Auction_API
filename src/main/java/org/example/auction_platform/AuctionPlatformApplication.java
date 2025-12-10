package org.example.auction_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AuctionPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuctionPlatformApplication.class, args);
    }

}
