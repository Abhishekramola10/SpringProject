package com.BootCampProject1.controller;

import com.BootCampProject1.BootCampProject1.JwtUtil;
import com.BootCampProject1.BootCampProject1.OTHERS.Status;
import com.BootCampProject1.BootCampProject1.repository.Repository;
import com.BootCampProject1.BootCampProject1.service.MyUserDetailsService;
import com.BootCampProject1.model.AuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class ControllerClass {

    @Autowired
    private Repository repository;

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public MyUserDetailsService userDetailsService;

    @Autowired
    public JwtUtil jwtTokenUtil;

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @PostMapping("/authenticate")    //accept user id and password and return JWT as response
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {   //if authentication_manager fails
            throw new Exception("Incorrect username or password", e);
        }

        //If Authentication done,we need to create a token as,
        // createAuthenticationToken needs to return a JWT

        final UserDetails userDetails = //accessing user detail service
                userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        String jwt = jwtTokenUtil.generateToken(userDetails);//creating token
        System.out.println(jwt);
        System.out.println("RAN");
        return new ResponseEntity<>(jwt, HttpStatus.OK);//mvc returns 200 OK
    }

    @GetMapping("/")
    public String home() {
        return ("<h1>Welcome</h1>");
    }

    @GetMapping("/user")
    public String user() {
        return ("<h1>Welcome USER</h1>");
    }

    @GetMapping("/admin")
    public String admin() {
        return ("<h1>Welcome Admin</h1>");
    }

    @DeleteMapping("/users/all")
    public Status deleteUsers() {
        repository.deleteAll();
        return Status.SUCCESS;
    }
}




