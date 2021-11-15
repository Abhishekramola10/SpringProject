package com.BootCampProject1.BootCampProject1.API;

import com.BootCampProject1.BootCampProject1.API.Register_API.validation;
import com.BootCampProject1.BootCampProject1.users.ADDRESS;
import com.BootCampProject1.BootCampProject1.users.SELLER;
import com.BootCampProject1.BootCampProject1.JwtUtil;
import com.BootCampProject1.BootCampProject1.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class Seller_API {
    //As a logged in Seller

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SellerRepository sellerRepository;

    //API to view my profile
    @GetMapping("/view/seller")
    public ResponseEntity<?>  ViewSeller(@RequestHeader String token)
    {
        String username = jwtUtil.extractUsername(token);
        Optional<SELLER> s = sellerRepository.findByEmail(username);
        if(s.isPresent()==false)
        {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
        Map<Object, Object> data = new HashMap<>();
        data.put("Seller Name", s.get().getFirstName());
        data.put("Seller ID", s.get().getId());
        //Add relevant details later
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    //API to update my profile
    @PutMapping("/update/seller/profile2")
    public ResponseEntity<?>  UpdateSellerProfile(@RequestHeader String token,
                                                  @RequestBody SELLER seller)
    {
        String username = jwtUtil.extractUsername(token);
        Optional<SELLER> s = sellerRepository.findByEmail(username);
        if(s.isPresent()==false)
        {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
        s.get().setFirstName(seller.getFirstName());
        s.get().setLastName(seller.getLastName());
        s.get().setAddress(seller.getAddress());
        s.get().setCompanyContact(seller.getCompanyContact());
        s.get().setCompanyName(seller.getCompanyName());
        s.get().setEmail(seller.getEmail());
        s.get().setRole(seller.getRole());
                //except email,id,token,GST,any Booleans,password
        //Add relevant details later
        sellerRepository.save(s.get());
        return new ResponseEntity<>("Updated",HttpStatus.OK);
    }

    //API to update my password
    @PutMapping("/update/seller/password2")
    public ResponseEntity<?>  UpdateSellerPassword(@RequestHeader String token,
                                                   @Valid @RequestBody SELLER seller,
                                                   BindingResult bindingResult)throws Exception {
        StringBuilder sb = new StringBuilder();
        if (bindingResult.hasErrors()) {  //checking not null
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError e : errors) {
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
    if(!validation.isValidEmailAddress(seller.getEmail()))
    {
        return new ResponseEntity<>("EMail is not valid", HttpStatus.BAD_REQUEST);
    }

        String username = jwtUtil.extractUsername(token);
        Optional<SELLER> s = sellerRepository.findByEmail(username);
        if (s.isPresent() == false) {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
        s.get().setPassword(seller.getPassword());
        sellerRepository.save(s.get());
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

    //API to update an address
    @PutMapping("/update/seller/password")
    public ResponseEntity<?>  UpdateSellerPassword(@RequestHeader String token,
                                                   @RequestBody ADDRESS address)
    {
        String username = jwtUtil.extractUsername(token);
        Optional<SELLER> s = sellerRepository.findByEmail(username);
        if(s.isPresent()==false)
        {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
        Set<ADDRESS> addresses = new HashSet<>();
        addresses.add(address);
        s.get().setAddress(addresses);
        sellerRepository.save(s.get());
        return new ResponseEntity<>("Updated",HttpStatus.OK);
    }
}
