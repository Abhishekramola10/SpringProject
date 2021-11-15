package com.BootCampProject1.BootCampProject1.API.Register_API;

import com.BootCampProject1.BootCampProject1.users.CUSTOMER;
import com.BootCampProject1.BootCampProject1.users.USER;
import com.BootCampProject1.BootCampProject1.JwtUtil;
import com.BootCampProject1.BootCampProject1.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class Customer_Register_API {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private Repository repository;

    @PostMapping("/users/register")     //Customer Register API
    public ResponseEntity<?> registerUser(@Valid @RequestBody CUSTOMER customer,
                                          BindingResult bindingResult)throws Exception
//ResponseEntity represents an HTTP response, including headers, body, and status.

    {                   //Validation
        System.out.println("I am running");
        StringBuilder sb = new StringBuilder();
        if(bindingResult.hasErrors()){              //checking not null
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError e : errors){
                sb.append("\n@" + e.getField().toUpperCase() + ":" + e.getDefaultMessage());
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        }



        //validating password
        if(!validation.isValid(customer.getPassword(),customer.getConfirmPassword(), new ArrayList<>()))
        {
            return new ResponseEntity<>("Invalid password or confirm password", HttpStatus.BAD_REQUEST);
        }

        //validating email
//        if(!validation.isValidEmailAddress(customer.getEmail()))
//        {
//            return new ResponseEntity<>("EMail is not valid", HttpStatus.BAD_REQUEST);
//        }

        //validating phone number
        if(!validation.isMobileNumberValid(customer.getPhoneNumber()))
        {
            return new ResponseEntity<>("Mobile Number not valid", HttpStatus.BAD_REQUEST);
        }


        Optional<USER> u1 = repository.findByEmail(customer.getEmail());
        //Optional is a container object used to contain not-null objects.
        if (u1.isPresent())
        {
            return new ResponseEntity<>("user already registered", HttpStatus.BAD_REQUEST);
        }
        String token = UUID.randomUUID().toString(); //activation token
        //The randomUUID() method is used to retrieve a type 4 (pseudo randomly generated) UUID
        customer.setToken(token);


//        if(jwtUtil.validateToken(token)==false){   //validating token
//            return new ResponseEntity<>("Invalid Token", HttpStatus.BAD_REQUEST);
//        }

     repository.save(customer);
        return new ResponseEntity<>(token,HttpStatus.OK);
        }
    }

