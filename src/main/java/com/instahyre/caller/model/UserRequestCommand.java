package com.instahyre.caller.model;

import lombok.Data;

@Data
public class UserRequestCommand {
    private String email;

    private String password;

    private String name;

    private String phone;
}
