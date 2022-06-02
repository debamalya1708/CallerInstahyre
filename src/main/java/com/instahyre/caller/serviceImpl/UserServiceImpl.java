package com.instahyre.caller.serviceImpl;

import com.instahyre.caller.daoService.UserDaoService;
import com.instahyre.caller.model.*;
import com.instahyre.caller.repository.UserContactRepository;
import com.instahyre.caller.repository.UserRepository;
import com.instahyre.caller.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserContactRepository userContactRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    UserDaoService userDaoService;

    @Override
    public User saveUser(User user) {
        UserRequestCommand userRequestCommand =
                modelMapper.map(user, UserRequestCommand.class);
        return userDaoService.register(userRequestCommand);
    }

    @Override
    public void saveUserContact(String userId, UserContact user) {
        user.setUserId(Long.parseLong(userId));
        UserContactRequestCommand userRequestCommand =
                modelMapper.map(user, UserContactRequestCommand.class);
        userDaoService.addContact(userRequestCommand);
    }

    @Override
    public User getUser(String contactNumber) {
        return userDaoService.getUser(contactNumber);
    }

    @Override
    public List<ContactResponse> searchUser(String userId,SearchRequest searchRequest) {
        if(searchRequest.getName() != null) {
            return findByName(searchRequest);
        }else if(searchRequest.getContact() != null){
            return findByContact(userId,searchRequest);
        }
        return null;
    }

        private List<ContactResponse> findByContact(String userId,SearchRequest searchRequest) {
        List<User> list = userDaoService.findContactsForRegisteredUser(searchRequest.getContact());
        if(list.size()>0){
            return list.stream().map(res -> {
                ContactResponse response = new ContactResponse();
                response.setContactName(res.getName());
                response.setSpamStatus(true);
                if (userId.equals(res.getId().toString())) {
                    response.setEmail(res.getEmail());
                } else {
                    response.setEmail(null);
                }
                response.setContactNumber(res.getPhone());
                return response;
            }).collect(Collectors.toList());
        }else{
            return userDaoService.findContactFromGlobal(searchRequest.getContact()).stream().map(res -> {

                ContactResponse response = new ContactResponse();
                response.setContactName(res.getContactName());
                response.setSpamStatus(res.isSpamStatus());
                if (userId.equals(res.getId().toString())) {
                    User user = userDaoService.getUser(res.getContactName());
                    response.setEmail(user.getEmail());
                } else {
                    response.setEmail(null);
                }
                response.setContactNumber(res.getContactNumber());
                return response;
            }).collect(Collectors.toList());
        }
    }

    private List<ContactResponse> findByName(SearchRequest searchRequest){
        return userDaoService.searchUser(searchRequest.getName()).stream().map(res -> {
            ContactResponse response = new ContactResponse();
            response.setContactName(res.getContactName());
            response.setSpamStatus(res.isSpamStatus());
            response.setContactNumber(res.getContactNumber());
            return response;
        }).collect(Collectors.toList());
    }
}
