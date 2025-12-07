package org.example.auction_platform.controller.account.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAccountResponse {
    private String name;
    private String email;
}
