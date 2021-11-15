package com.BootCampProject1.BootCampProject1.API;

import com.BootCampProject1.BootCampProject1.API.Register_API.validation;
import com.BootCampProject1.BootCampProject1.users.ADDRESS;
import com.BootCampProject1.BootCampProject1.users.CUSTOMER;
import com.BootCampProject1.BootCampProject1.JwtUtil;
import com.BootCampProject1.BootCampProject1.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class Customer_API {

        //As a Logged in Customer

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerRepository customerRepository;

    //API to view my profile
    @GetMapping("/view/customer")
    public ResponseEntity<?> ViewCustomer(@RequestHeader String token)
    {
        String username = jwtUtil.extractUsername(token);
        Optional<CUSTOMER> s = customerRepository.findByEmail(username);
        if(s.isPresent()==false)
        {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
        Map<Object, Object> data = new HashMap<>();
        data.put("Customer Name", s.get().getFirstName());
        data.put("Customer ID", s.get().getId());
        //Add relevant details later
        return new ResponseEntity<>(data,HttpStatus.OK);
    }


    //API to view my address
    @GetMapping("/view/customer/address")
    public ResponseEntity<?> ViewCustomerAddress(@RequestHeader String token,
                                                 )
    {
        String username = jwtUtil.extractUsername(token);
        Optional<CUSTOMER> s = customerRepository.findByEmail(username);
        if(s.isPresent()==false)
        {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
        Map<Object, Object> data = new HashMap<>();
        data.put("Address",s.get().getAddress());
        //Add relevant details later
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    //API to update my profile
    @PutMapping("/update/customer/profile")
    public ResponseEntity<?>  UpdateCustomerProfile(@RequestHeader String token,
                                                  @RequestBody CUSTOMER customer)
    {
        String username = jwtUtil.extractUsername(token);
        Optional<CUSTOMER> s = customerRepository.findByEmail(username);
        if(s.isPresent()==false)
        {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
        s.get().setPhoneNumber(customer.getPhoneNumber());
        //Add relevant details later
        customerRepository.save(s.get());
        return new ResponseEntity<>("Updated",HttpStatus.OK);
    }

    //API to update my password
    @PutMapping("/update/customer/password")
    public ResponseEntity<?>  UpdateCustomerPassword(@Valid @RequestHeader String token,
                                                   @RequestBody CUSTOMER customer)
    {
        String username = jwtUtil.extractUsername(token);
        Optional<CUSTOMER> s = customerRepository.findByEmail(username);
        if(s.isPresent()==false)
        {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
        s.get().setPassword(customer.getPassword());
        customerRepository.save(s.get());
        return new ResponseEntity<>("Updated",HttpStatus.OK);
    }

    //API to add a new address
    @PostMapping("/add/customer/new_address")
    public ResponseEntity<?>  AddCustomerAddress(@RequestHeader String token,
                                                    @RequestBody ADDRESS address,
                                                 @Valid @RequestBody CUSTOMER customer,
                                                 BindingResult bindingResult)throws Exception
    {
                StringBuilder sb = new StringBuilder();
        if (bindingResult.hasErrors()) {  //checking not null
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError e : errors) {
                sb.append("\n@" + e.getField().toUpperCase() + ":" + e.getDefaultMessage());
            }
            return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        //validation: checking pattern
        if(!validation.isValid(customer.getPassword(),customer.getConfirmPassword(), new ArrayList<>()))
        {
            return new ResponseEntity<>("Invalid password ", HttpStatus.BAD_REQUEST);
        }

        //validating email
        if(!validation.isValidEmailAddress(customer.getEmail()))
        {
            return new ResponseEntity<>("EMail is not valid", HttpStatus.BAD_REQUEST);
        }

        //validating phone number
        if(!validation.isMobileNumberValid(customer.getPhoneNumber()))
        {
            return new ResponseEntity<>("EMail is not valid", HttpStatus.BAD_REQUEST);
        }

        String username = jwtUtil.extractUsername(token);
        Optional<CUSTOMER> s = customerRepository.findByEmail(username);
        if(s.isPresent()==false)
        {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
        Set<ADDRESS> addresses = s.get().getAddress();
        addresses.add(address);
        s.get().setAddress(addresses);
        customerRepository.save(s.get());
        return new ResponseEntity<>("Updated",HttpStatus.OK);
    }

    //API to delete an address
    @DeleteMapping("/delete/customer/address/{id}") //valid and existing id is to be passed
    public ResponseEntity<?>  AddCustomerAddress(@Valid @RequestHeader String token,
                                                 @PathVariable Long id)
    {
        String username = jwtUtil.extractUsername(token);
        Optional<CUSTOMER> s = customerRepository.findByEmail(username);
        if(s.isPresent()==false)
        {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
        Set<ADDRESS> addresses = s.get().getAddress();
        addresses = addresses.stream().filter(p->p.getID()!=id).collect(Collectors.toSet());
        s.get().setAddress(addresses);
        customerRepository.save(s.get());
        return new ResponseEntity<>("Updated",HttpStatus.OK);
    }


    //API to update an address
    @PutMapping("/update/customer/address")
    public ResponseEntity<?>  UpdateCustomerAddress(@RequestHeader String token,
                                                   @RequestBody ADDRESS address)
    {
        String username = jwtUtil.extractUsername(token);
        Optional<CUSTOMER> s = customerRepository.findByEmail(username);
        if(s.isPresent()==false)
        {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
        Set<ADDRESS> addresses = new HashSet<>();
        addresses.add(address);
        s.get().setAddress(addresses);
        customerRepository.save(s.get());
        return new ResponseEntity<>("Updated",HttpStatus.OK);
    }
}
