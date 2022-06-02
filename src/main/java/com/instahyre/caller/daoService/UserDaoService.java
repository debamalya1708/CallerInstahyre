package com.instahyre.caller.daoService;

import com.instahyre.caller.model.User;
import com.instahyre.caller.model.UserContact;
import com.instahyre.caller.model.UserContactRequestCommand;
import com.instahyre.caller.model.UserRequestCommand;

import java.util.Collection;
import java.util.List;

public interface UserDaoService {

    User register(UserRequestCommand userRequestCommand);

    void addContact(UserContactRequestCommand userRequestCommand);

    User getUser(String contactNumber);

    List<UserContact> searchUser(String name);

    List<UserContact> findContactFromGlobal(String contact);

    List<User> findContactsForRegisteredUser(String contact);
}
