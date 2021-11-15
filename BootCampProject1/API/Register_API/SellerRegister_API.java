package com.BootCampProject1.BootCampProject1.API.Register_API;

import com.BootCampProject1.BootCampProject1.users.SELLER;
import com.BootCampProject1.BootCampProject1.users.USER;
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
public class SellerRegister_API {

    @Autowired
    private Repository repository;

    @PostMapping("/sellers/register")     //Seller Register API
    public ResponseEntity<?> registerSeller(@Valid @RequestBody SELLER seller,
                                            BindingResult bindingResult)throws Exception

        {                 // Validation
        StringBuilder sb = new StringBuilder();
        if(bindingResult.hasErrors()){              //checking not null
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError e : errors){
                sb.append("\n@" + e.getField().toUpperCase() + ":" + e.getDefaultMessage());
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
            //validating password
            if(!validation.isValid(seller.getPassword(),seller.getConfirmPassword(), new ArrayList<>()))
            {
                return new ResponseEntity<>("Invalid password or confirm password", HttpStatus.BAD_REQUEST);
            }

            //validating email
//            if(!validation.isValidEmailAddress(seller.getEmail()))
//            {
//                System.out.println("i ran too");
//                return new ResponseEntity<>("EMail is not valid", HttpStatus.BAD_REQUEST);
//            }

            //validating gst
            if(!validation.isValidGST(seller.getGST()))
            {
                return new ResponseEntity<>("GST is not valid", HttpStatus.BAD_REQUEST);
            }

            //validating PhoneNumber
            if(!validation.isMobileNumberValid(seller.getCompanyContact()))
            {
                return new ResponseEntity<>("PhoneNumber is not valid", HttpStatus.BAD_REQUEST);
            }


        Optional<USER> u2 = repository.findByEmail(seller.getEmail());
        if (u2.isPresent())
        {
            return new ResponseEntity<>("Seller already registered", HttpStatus.BAD_REQUEST);
        }
        String tokens = UUID.randomUUID().toString();
        seller.setToken(tokens);
        repository.save(seller);
        return new ResponseEntity<>(tokens,HttpStatus.OK);
    }
}
