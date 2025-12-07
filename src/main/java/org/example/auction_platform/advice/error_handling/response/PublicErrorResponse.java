package org.example.auction_platform.advice.error_handling.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicErrorResponse {
    private String error;
}
