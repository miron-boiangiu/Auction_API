package org.example.auction_platform.controller.bid;

import lombok.AllArgsConstructor;
import org.example.auction_platform.controller.bid.request.AddBidRequest;
import org.example.auction_platform.exception.UserInvalidInputException;
import org.example.auction_platform.service.bid.BidService;
import org.example.auction_platform.validator.UserInputValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/listing/{listingId}")
@AllArgsConstructor
public class BidController {

    private BidService bidService;

    @PostMapping("/bid")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBid(
            @PathVariable long listingId,
            @RequestBody AddBidRequest request
    ) {

        if(!UserInputValidator.isValidEmail(request.getBidderEmail())) {
            throw new UserInvalidInputException("Invalid email.");
        }

        if(!UserInputValidator.isValidValue(request.getValue())) {
            throw new UserInvalidInputException("Invalid value.");
        }

        bidService.addBid(
                request.getBidderEmail(),
                request.getValue(),
                listingId
        );
    }
}
