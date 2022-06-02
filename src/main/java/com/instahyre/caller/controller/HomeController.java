package com.instahyre.caller.controller;

import com.instahyre.caller.config.JwtResponse;
import com.instahyre.caller.helper.JwtUtil;
import com.instahyre.caller.model.Login;
import com.instahyre.caller.model.User;
import com.instahyre.caller.service.CustomUserDetailsService;
import com.instahyre.caller.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value="/user")
public class HomeController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody Login login) throws Exception{
        try {
            authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword()));
        }catch(BadCredentialsException ex) {
            new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(login.getUserName());
        User user  = userService.getUser(login.getUserName());
        final String jwt = jwtUtil.generateToken(user,userDetails);
        return new ResponseEntity<>(new JwtResponse(jwt),HttpStatus.OK);
    }

    @PostMapping("/register")
    public void register(@RequestBody User user){
        userService.saveUser(user);
    }
}
