package com.BootCampProject1.BootCampProject1.API.Register_API;

import com.BootCampProject1.BootCampProject1.users.CUSTOMER;
import com.BootCampProject1.BootCampProject1.users.USER;
import com.BootCampProject1.BootCampProject1.JwtUtil;
import com.BootCampProject1.BootCampProject1.service.MyUserDetailsService;
import com.BootCampProject1.BootCampProject1.repository.Repository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
public class Activation_Token {

    @Autowired
    Repository repository;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    MyUserDetailsService myUserDetailsService;

//Activating account using Activation token
    @PutMapping("/activate_customer")
    public ResponseEntity<String> activateAccount(String token) throws IOException {
        try {
            Optional<USER> user = repository.findByEmail(jwtUtil.extractUsername(token));
            System.out.println(jwtUtil.extractUsername(token));
            System.out.println(user.get().toString());

            if (!user.isPresent()) {
                return new ResponseEntity<>("NO such user exists, check token or generate new token", HttpStatus.BAD_REQUEST);
            } else {
                CUSTOMER customer = (CUSTOMER) user.get();
                System.out.println(customer.toString());
                user.get().setActive(true);
                repository.save(user.get());
//                emailSenderService.setMailSender(user.get().getEmail(), "Default email", "Your account is now live");
                return new ResponseEntity<>("Account is Live", HttpStatus.OK);
            }
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("The token was expired", HttpStatus.BAD_REQUEST);
        } catch (SignatureException e) {
            return new ResponseEntity<String>("Token signature mismatch,check token", HttpStatus.BAD_REQUEST);
        }
    }

//gives New Activation token when earlier expires-API to resend activation link
        @PostMapping("/resend_activation_link")
        public ResponseEntity<String> newActivationToken(String email){
            //TODO: ADD CHECK

            UserDetails u = myUserDetailsService.loadUserByUsername(email);
            //EmailVarificationHelper emailVarificationHelper = new EmailVarificationHelper();
//            varificationHelper.setEmail(u.getEmail());
//            varificatonHelper.setPassword(u.getPassword());
//            varificatonHelper.setGrantAuthorities(u.getGrantAuthorities());

            String token = jwtUtil.generateToken(u);
            USER user = repository.findByEmail(email).get();
            user.setToken(token);
            repository.save(user);
            return new ResponseEntity<>(token, HttpStatus.CREATED);
        }
}


