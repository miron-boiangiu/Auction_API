package org.example.auction_platform.controller.listing;

import org.example.auction_platform.controller.listing.mapper.GetListingMapper;
import org.example.auction_platform.controller.listing.request.CreateListingRequest;
import org.example.auction_platform.controller.listing.response.CreateListingResponse;
import org.example.auction_platform.controller.listing.response.GetListingResponse;
import org.example.auction_platform.exception.UserInvalidInputException;
import org.example.auction_platform.repository.listing.entity.FinishedListing;
import org.example.auction_platform.repository.listing.entity.Listing;
import org.example.auction_platform.repository.listing.entity.OngoingListing;
import org.example.auction_platform.service.listing.ListingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.example.auction_platform.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListingControllerTest {

    @Mock
    private ListingService listingService;

    @Mock
    private GetListingMapper getListingMapper = new GetListingMapper();

    @InjectMocks
    private ListingController controller;

    @Test
    public void givenValidData_whenCreateNewListing_thenCallServiceAndReturnId() {
        CreateListingRequest request = new CreateListingRequest();
        request.setStartingValue(STARTING_PRICE);
        request.setCreatorEmail(EMAIL);
        request.setListingName(ITEM_NAME);

        Listing mockListing = mock(Listing.class);
        when(mockListing.getId()).thenReturn(LISTING_ID);
        when(listingService.createNewListing(EMAIL, STARTING_PRICE, ITEM_NAME))
                .thenReturn(mockListing);

        ResponseEntity<CreateListingResponse> response = controller.createNewListing(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(LISTING_ID, response.getBody().getId());
        verify(listingService).createNewListing(EMAIL, STARTING_PRICE, ITEM_NAME);
    }

    @Test
    public void givenInvalidValue_whenCreateNewListing_thenThrow() {
        CreateListingRequest request = new CreateListingRequest();
        request.setStartingValue(-10);
        request.setCreatorEmail(EMAIL);
        request.setListingName(ITEM_NAME);

        assertThrows(UserInvalidInputException.class,
                () -> controller.createNewListing(request));

        verify(listingService, times(0)).createNewListing(anyString(), anyLong(), anyString());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "", "invalid-email" })
    public void givenInvalidEmail_whenCreateNewListing_thenThrow(String email) {
        CreateListingRequest request = new CreateListingRequest();
        request.setStartingValue(STARTING_PRICE);
        request.setCreatorEmail(email);
        request.setListingName(ITEM_NAME);

        assertThrows(UserInvalidInputException.class,
                () -> controller.createNewListing(request));

        verify(listingService, times(0)).createNewListing(anyString(), anyLong(), anyString());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = { "" })
    public void givenInvalidName_whenCreateNewListing_thenThrow(String name) {
        CreateListingRequest request = new CreateListingRequest();
        request.setStartingValue(STARTING_PRICE);
        request.setCreatorEmail(EMAIL);
        request.setListingName(name);

        assertThrows(UserInvalidInputException.class,
                () -> controller.createNewListing(request));

        verify(listingService, times(0)).createNewListing(anyString(), anyLong(), anyString());
    }

    @Test
    public void givenExistingId_whenGetListing_thenReturnOk() {
        Listing mockListing = mock(Listing.class);
        GetListingResponse mockResponse = mock(GetListingResponse.class);

        when(listingService.getListing(LISTING_ID)).thenReturn(Optional.of(mockListing));
        when(mockListing.accept(getListingMapper)).thenReturn(mockResponse);

        ResponseEntity<GetListingResponse> response = controller.getListing(LISTING_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    public void givenNonExistingId_whenGetListing_thenReturnNotFound() {
        when(listingService.getListing(LISTING_ID)).thenReturn(Optional.empty());

        ResponseEntity<GetListingResponse> response = controller.getListing(LISTING_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void whenGetOngoingListings_thenCallServiceAndMap() {
        OngoingListing mockListing = mock(OngoingListing.class);
        GetListingResponse mockResponse = mock(GetListingResponse.class);

        when(listingService.getOngoingListings()).thenReturn(List.of(mockListing));
        when(getListingMapper.visit(mockListing)).thenReturn(mockResponse);

        List<GetListingResponse> results = controller.getOngoingListings();

        assertEquals(1, results.size());
        assertEquals(mockResponse, results.get(0));
        verify(listingService).getOngoingListings();
    }

    @Test
    public void givenId_whenEndListing_thenCallService() {
        controller.endListing(LISTING_ID);

        verify(listingService).endListing(LISTING_ID);
    }

    @Test
    public void whenGetFinishedListings_thenCallServiceAndMap() {
        FinishedListing mockListing = mock(FinishedListing.class);
        GetListingResponse mockResponse = mock(GetListingResponse.class);

        when(listingService.getFinishedListings()).thenReturn(List.of(mockListing));
        when(getListingMapper.visit(mockListing)).thenReturn(mockResponse);

        List<GetListingResponse> results = controller.getFinishedListings();

        assertEquals(1, results.size());
        assertEquals(mockResponse, results.get(0));
        verify(listingService).getFinishedListings();
    }
}