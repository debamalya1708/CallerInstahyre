package com.instahyre.caller.controller;


import com.instahyre.caller.config.JwtResponse;
import com.instahyre.caller.helper.JwtUtil;
import com.instahyre.caller.model.*;
import com.instahyre.caller.service.CustomUserDetailsService;
import com.instahyre.caller.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="/user/contact")
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    JwtUtil jwtUtil;


    @PostMapping()
    public ResponseEntity<Void> saveUserContact(HttpServletRequest request, @RequestBody UserContact user){
        Map<String, String> map = jwtUtil.getJwtTokenDetails(request);
        userService.saveUserContact(map.get(CallerConstants.userId),user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<User> getUserDetails(HttpServletRequest request){
        Map<String, String> map = jwtUtil.getJwtTokenDetails(request);
        return ResponseEntity.ok(userService.getUser(map.get(CallerConstants.contactNo)));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ContactResponse>> searchUser(HttpServletRequest request , @RequestParam(value="name", required = false) String name,
                                                            @RequestParam(value="contact", required = false) String contact){
        Map<String, String> map = jwtUtil.getJwtTokenDetails(request);
        log.info("userid: {}", map.get(CallerConstants.userId));
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setContact(contact);
        searchRequest.setName(name);
        log.info("searchRequest: {}", searchRequest);
        return ResponseEntity.ok(userService.searchUser(map.get(CallerConstants.userId),searchRequest));
    }
//    @GetMapping("/search/{contact}/contact")
//    public ResponseEntity<List<ContactResponse>> searchUserByContact(@PathVariable String contact ){
//
//        log.info("contact:", contact);
//        SearchRequest searchRequest = new SearchRequest();
////        searchRequest.setContact(name);
//        searchRequest.setName(contact);
//        log.info("searchRequest:", searchRequest);
//        return ResponseEntity.ok(userService.searchUser(searchRequest));
//    }




}

