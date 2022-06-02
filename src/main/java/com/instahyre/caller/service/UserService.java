package com.instahyre.caller.service;

import com.instahyre.caller.model.ContactResponse;
import com.instahyre.caller.model.SearchRequest;
import com.instahyre.caller.model.User;
import com.instahyre.caller.model.UserContact;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    void saveUserContact(String contactNumber, UserContact user);
    User getUser(String contactNumber);

    List<ContactResponse> searchUser(String userId,SearchRequest searchRequest);
}
