package org.example.auction_platform.controller.listing.mapper;

import org.example.auction_platform.controller.listing.response.GetFinishedListingResponse;
import org.example.auction_platform.controller.listing.response.GetListingResponse;
import org.example.auction_platform.controller.listing.response.GetOngoingListingResponse;
import org.example.auction_platform.repository.listing.entity.FinishedListing;
import org.example.auction_platform.repository.listing.entity.OngoingListing;
import org.example.auction_platform.repository.listing.entity.visitor.ListingVisitor;
import org.springframework.stereotype.Component;

@Component
public class GetListingMapper implements ListingVisitor<GetListingResponse> {

    @Override
    public GetListingResponse visit(OngoingListing listing) {
        long currentPrice = listing.getStartingPrice();

        if (!listing.getBids().isEmpty()) {
            currentPrice = listing.getBids().get(0).getValue();
        }

        return GetOngoingListingResponse.builder()
                .itemName(listing.getItemName())
                .creatorEmail(listing.getListingCreator().getEmail())
                .startingPrice(listing.getStartingPrice())
                .currentPrice(currentPrice)
                .numberOfBids(listing.getBids().size())
                .build();
    }

    @Override
    public GetListingResponse visit(FinishedListing listing) {
        return GetFinishedListingResponse.builder()
                .itemName(listing.getItemName())
                .creatorEmail(listing.getListingCreator().getEmail())
                .winnerEmail(listing.getBiddingWinner().getEmail())
                .build();
    }
}