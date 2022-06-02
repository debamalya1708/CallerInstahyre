package com.instahyre.caller.daoServiceImpl;

import com.instahyre.caller.daoService.UserDaoService;
import com.instahyre.caller.model.User;
import com.instahyre.caller.model.UserContact;
import com.instahyre.caller.model.UserContactRequestCommand;
import com.instahyre.caller.model.UserRequestCommand;
import com.instahyre.caller.repository.UserContactRepository;
import com.instahyre.caller.repository.UserRepository;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDaoServiceImpl implements UserDaoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserContactRepository userContactRepository;

    @Override
    public User register(UserRequestCommand userRequestCommand) {
        Option<User> user = userRepository.findByPhone(userRequestCommand.getPhone());
        if(user.isEmpty()){
            return userRepository.save(User.builder().password(userRequestCommand.getPassword()).
                    name(userRequestCommand.getName()).phone(userRequestCommand.getPhone())
                    .email(userRequestCommand.getEmail()).build());
        }
        else{
            throw new BadCredentialsException("User already exists!");
        }
    }

    @Override
    public void addContact(UserContactRequestCommand userContactRequestCommand) {
        userContactRepository.save(UserContact.builder().userId(userContactRequestCommand.getUserId())
                .contactName(userContactRequestCommand.getContactName()).contactNumber(userContactRequestCommand.getContactNumber())
                .spamStatus(userContactRequestCommand.isSpamStatus()).build());
    }

    @Override
    public User getUser(String contactNumber) {
        Option<User> userOption = userRepository.findByPhone(contactNumber);
        if(!userOption.isEmpty()) {
            User user = userOption.get();
            return User.builder().name(user.getName()).id(user.getId())
                    .phone(user.getPhone())
                    .email(user.getEmail()).build();
        }
        return null;
    }

    @Override
    public List<UserContact> searchUser(String name) {
        return userContactRepository.findByContactNameContainingOrderByContactName(name);
    }

    @Override
    public List<UserContact> findContactFromGlobal(String contact) {
        return userContactRepository.findByContactNumberOrderByContactName(contact);
    }

    @Override
    public List<User> findContactsForRegisteredUser(String contact) {
        return userRepository.findByPhoneOrderByName(contact);
    }
}
