package org.example.auction_platform.controller.bid.mapper;

import org.example.auction_platform.controller.bid.response.BidResponse;
import org.example.auction_platform.repository.bid.entity.Bid;
import org.springframework.stereotype.Component;

@Component
public class BidMapper {

    public BidResponse entityToResponse(Bid bid) {

        return BidResponse.builder()
                .bidderEmail(bid.getBidder().getEmail())
                .value(bid.getValue())
                .build();
    }
}
