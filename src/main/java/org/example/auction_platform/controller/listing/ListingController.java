package org.example.auction_platform.controller.listing;

import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.example.auction_platform.controller.listing.request.CreateListingRequest;
import org.example.auction_platform.exception.UserInvalidInputException;
import org.example.auction_platform.service.listing.ListingService;
import org.example.auction_platform.validator.UserInputValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listing")
@AllArgsConstructor
public class ListingController {

    private ListingService listingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewListing(@RequestBody CreateListingRequest request) {

        if(!UserInputValidator.isValidStartingValue(request.getStartingValue())) {
            throw new UserInvalidInputException("Invalid starting value.");
        }

        if(!UserInputValidator.isValidEmail(request.getCreatorEmail())) {
            throw new UserInvalidInputException("Invalid starting value.");
        }

        if(!UserInputValidator.isValidItemName(request.getListingName())) {
            throw new UserInvalidInputException("Invalid listing name.");
        }

        listingService.createNewListing(
                request.getCreatorEmail(),
                request.getStartingValue(),
                request.getListingName()
        );
    }
}
