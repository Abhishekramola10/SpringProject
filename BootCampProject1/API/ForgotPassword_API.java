package com.BootCampProject1.BootCampProject1.API;

import com.BootCampProject1.BootCampProject1.API.Register_API.validation;
import com.BootCampProject1.BootCampProject1.users.USER;
import com.BootCampProject1.BootCampProject1.JwtUtil;
import com.BootCampProject1.BootCampProject1.service.MyUserDetailsService;
import com.BootCampProject1.BootCampProject1.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ForgotPassword_API {

    @Autowired
    private Repository repository;

    @Autowired
    private JwtUtil jwtUtil;
    //username and password to api

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @PostMapping("/ForgotPassword")     //API to receive a token based URL
    public ResponseEntity<?> registerUser(@RequestHeader String token,
                                          @Valid @RequestBody USER newUser,
                                          BindingResult bindingResult)throws Exception
    {
        //  Validation
        StringBuilder sb = new StringBuilder();
        if(bindingResult.hasErrors()){              //checking not null
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError e : errors){
                sb.append("\n@" + e.getField().toUpperCase() + ":" + e.getDefaultMessage());
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        //validating email
        if(!validation.isValidEmailAddress(newUser.getEmail()))
        {
            return new ResponseEntity<>("EMail is not valid", HttpStatus.BAD_REQUEST);
        }

        //checking if user(email) already exists
        String username = jwtUtil.extractUsername(token);//taking username from token
        Optional<USER> u1 = repository.findByEmail(username);//get email from user
        if(u1.isPresent()==false && u1.get().getToken().equals(token)) { //also recent token
            return new ResponseEntity<>("No such User", HttpStatus.BAD_REQUEST);
        }
        u1.get().setPassword(newUser.getPassword());
        repository.save(u1.get());
        return new ResponseEntity<>("Password Updated",HttpStatus.OK);
    }

    @PostMapping("/ForgotToken")        //API to reset the password using token
    public ResponseEntity<?> responseEntity(@RequestBody USER user)
    {
        Optional<USER> u1 = repository.findByEmail(user.getEmail());//get email from user
        if(u1.isPresent()==false ) {
            return new ResponseEntity<>("No such User", HttpStatus.BAD_REQUEST);
        }
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        u1.get().setToken(token);

        repository.save(u1.get());
        return new ResponseEntity<>(token,HttpStatus.CREATED);

    }
}
