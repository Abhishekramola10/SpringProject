package com.BootCampProject1.BootCampProject1.API.Login_Logout_API;

import com.BootCampProject1.BootCampProject1.API.Register_API.validation;
import com.BootCampProject1.BootCampProject1.users.SELLER;
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

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RestController
public class Seller_User {

    @Autowired
    private Repository repository;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/seller_user/login")    // API to LOG-INTO the system as seller user


    private ResponseEntity<?> LoginSellerUser(@RequestBody SELLER seller){


        Optional<USER> slr = repository.findByEmail(seller.getEmail());
        if (!slr.isPresent())
        {
            return new ResponseEntity<>("seller user not present", HttpStatus.BAD_REQUEST);
        }

        //validating password
        if(!slr.get().getPassword().equals(seller.getPassword())){
            return new ResponseEntity<>("Invalid Crediantials", HttpStatus.BAD_REQUEST);
        }

        UserDetails appUser = myUserDetailsService.loadUserByUsername(seller.getEmail());
        String token2 = jwtUtil.generateToken(appUser);
        String token = UUID.randomUUID().toString();

//                if(jwtUtil.validateToken(token)==false){   //validating token
//            return new ResponseEntity<>("Invalid Token", HttpStatus.BAD_REQUEST);
//        }
//
//        if(jwtUtil.isTokenExpired(token)==false){   //checking token expiration
//            return new ResponseEntity<>("Invalid Token", HttpStatus.BAD_REQUEST);
//        }

        slr.get().setToken(token2);
        return new ResponseEntity<>(token2,HttpStatus.OK);
    }


    //API to logout ,logged-in seller user
    @PostMapping("/seller_user/logout")
    public ResponseEntity<?> logSellerUserOut(@Valid @RequestBody USER user) {
        Optional<USER> u2b = repository.findByEmail(user.getEmail());
        if (!u2b.isPresent())
        {
            return new ResponseEntity<>("user not present", HttpStatus.BAD_REQUEST);
        }
        String token2 = UUID.randomUUID().toString();
        u2b.get().setToken(null);
        return new ResponseEntity<>(token2,HttpStatus.OK);
    }
}
