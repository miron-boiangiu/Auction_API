package org.example.auction_platform.controller.account.request;

import lombok.Data;

@Data
public class AddAccountRequest {  // TODO (not for this dl): AAA, passwords and stuff
    private String name;
    private String email;
}
