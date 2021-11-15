package com.BootCampProject1.BootCampProject1.API.Login_Logout_API;

import com.BootCampProject1.BootCampProject1.API.Register_API.validation;
import com.BootCampProject1.BootCampProject1.users.CUSTOMER;
import com.BootCampProject1.BootCampProject1.users.USER;
import com.BootCampProject1.BootCampProject1.JwtUtil;
import com.BootCampProject1.BootCampProject1.service.MyUserDetailsService;
import com.BootCampProject1.BootCampProject1.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
public class Customer_User {

    @Autowired
    private Repository repository;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/customer_user/login")    // API to LOG-INTO the system as customer user
    private ResponseEntity<?> LoginCustomerUser(@RequestBody CUSTOMER customer){


        Optional<USER> usr = repository.findByEmail(customer.getEmail());
        if (!usr.isPresent())
        {
            return new ResponseEntity<>("customer user not present", HttpStatus.BAD_REQUEST);
        }

        //validating password
        if(!usr.get().getPassword().equals(customer.getPassword())){
            return new ResponseEntity<>("Invalid Crediantials", HttpStatus.BAD_REQUEST);
        }
        UserDetails appUser = myUserDetailsService.loadUserByUsername(customer.getEmail());
        String token2 = jwtUtil.generateToken(appUser);
        String token = UUID.randomUUID().toString();
        usr.get().setToken(token2);
        return new ResponseEntity<>(token2,HttpStatus.OK);
    }

    //API to logout ,logged-in customer user
    @PostMapping("/customer_user/logout")
    public ResponseEntity<?> logCustomerUserOut(@RequestBody CUSTOMER customer) {
        Optional<USER> u2a = repository.findByEmail(customer.getEmail());
        if (!u2a.isPresent())
        {
            return new ResponseEntity<>("user not present", HttpStatus.BAD_REQUEST);
        }
        String token2 = UUID.randomUUID().toString();
        u2a.get().setToken(null);
        return new ResponseEntity<>(token2,HttpStatus.OK);

    }
}
