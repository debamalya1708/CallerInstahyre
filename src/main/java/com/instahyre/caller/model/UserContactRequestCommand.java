package com.instahyre.caller.model;

import lombok.Data;

@Data
public class UserContactRequestCommand {
    private Long userId;

    private String contactName;

    private String contactNumber;

    private boolean spamStatus;
}
