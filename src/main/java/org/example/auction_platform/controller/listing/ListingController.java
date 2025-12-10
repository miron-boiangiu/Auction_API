package org.example.auction_platform.controller.listing;

import lombok.AllArgsConstructor;
import org.example.auction_platform.controller.listing.mapper.GetListingMapper;
import org.example.auction_platform.controller.listing.request.CreateListingRequest;
import org.example.auction_platform.controller.listing.response.CreateListingResponse;
import org.example.auction_platform.controller.listing.response.GetListingResponse;
import org.example.auction_platform.exception.UserInvalidInputException;
import org.example.auction_platform.repository.listing.entity.Listing;
import org.example.auction_platform.service.listing.ListingService;
import org.example.auction_platform.validator.UserInputValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listing")
@AllArgsConstructor
public class ListingController {

    private ListingService listingService;

    private GetListingMapper getListingMapper;

    @PostMapping
    public ResponseEntity<CreateListingResponse> createNewListing(@RequestBody CreateListingRequest request) {

        if(!UserInputValidator.isValidValue(request.getStartingValue())) {
            throw new UserInvalidInputException("Invalid starting value.");
        }

        if(!UserInputValidator.isValidEmail(request.getCreatorEmail())) {
            throw new UserInvalidInputException("Invalid starting value.");
        }

        if(!UserInputValidator.isValidItemName(request.getListingName())) {
            throw new UserInvalidInputException("Invalid listing name.");
        }

        Listing newListing = listingService.createNewListing(
                request.getCreatorEmail(),
                request.getStartingValue(),
                request.getListingName()
        );

        return new ResponseEntity<>(
                CreateListingResponse.builder()
                        .id(newListing.getId())
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetListingResponse> getListing(@PathVariable long id) {

        return listingService.getListing(id) // Returns Optional<Listing>
                .map(listing -> listing.accept(getListingMapper)) // Polymorphic dispatch!
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
