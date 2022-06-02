package com.instahyre.caller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import javax.persistence.Column;

@Data
public class ContactResponse {

    private String contactName;

    private String contactNumber;

    private boolean spamStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;
}
