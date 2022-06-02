package com.instahyre.caller.service;

import com.instahyre.caller.repository.UserRepository;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    String password =null;
    public void getUser(String password){
        this.password=password;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        log.info("#  Finding user with mobile - {}", userName);

        Option<com.instahyre.caller.model.User> user =userRepository.findByPhone(userName);

        if(!user.isEmpty()){
            log.info("user - {}", user);
           return new User(user.get().getPhone(), user.get().getPassword(),new ArrayList<>());
        }
        else{
            throw  new UsernameNotFoundException("User not found!");
        }

//        UserDetails userDetails = null;
//
//        Option<User> user = userRepository.findByMobile(userName.trim());
//
//        if(!user.isEmpty()){
//            log.info("User found");
//
//            User u = user.get();
//
//            log.info("User found - {}", u);
//
//            log.info("User pwd - {}", u.getPassword());
//
//            userDetails = DefaultUserDetails.builder()
//                    .id(u.getId())
//                    .userName(userName)
//                    .deleted(u.isDeleted())
//                    .role(u.getRole().getName())
//                    .password(u.getPassword()).build();
//
//            log.info("User authorities - {}", userDetails.getAuthorities());
//            log.info("Roles - {}", u.getRole());
//        } else {
//            throw new UsernameNotFoundException("User not found with username - " + userName );
//        }
//        return userDetails;
    }

}
