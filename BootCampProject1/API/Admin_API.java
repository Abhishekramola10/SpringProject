package com.BootCampProject1.BootCampProject1.API;

import com.BootCampProject1.BootCampProject1.users.CUSTOMER;
import com.BootCampProject1.BootCampProject1.users.SELLER;
import com.BootCampProject1.BootCampProject1.repository.CustomerRepository;
import com.BootCampProject1.BootCampProject1.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class Admin_API {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SellerRepository sellerRepository;

    //API to get all registered customers
    @GetMapping("/users/customers")
    public ResponseEntity<?> listCustomers() {
        List<CUSTOMER> customerList = customerRepository.findAll();
        List<Map<Object, Object>> data2 = new ArrayList<>();
        for (CUSTOMER s : customerList) {
            Map<Object, Object> data = new HashMap<>();
            data.put("Customer Name", s.getFirstName());
            data.put("Customer ID", s.getId());
            //ADD all relevant details as required
            data2.add(data);
        }
        return new ResponseEntity<>(data2, HttpStatus.OK);
    }


    //API to get all registered sellers
    @GetMapping("/users/sellers")
    public ResponseEntity<?> listSellers() {
        List<SELLER> sellerList = sellerRepository.findAll();
        List<Map<Object, Object>> data2 = new ArrayList<>();
        for (SELLER s : sellerList) {
            Map<Object, Object> data = new HashMap<>();
            data.put("Seller Name", s.getFirstName());
            data.put("Seller ID", s.getId());
            //ADD all relevant details as required
            data2.add(data);
        }
        return new ResponseEntity<>(data2, HttpStatus.OK);
    }


    //API to activate a customer
    @PutMapping("/admin/activate/customer/{id}")
    public ResponseEntity<?> ActivateCustomer(@PathVariable Long id) {
        //checking id validity
        Optional<CUSTOMER> customer = customerRepository.findById(id);
        if (customer.isPresent() == false) {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }
        if(!customer.get().isActive()){
            return new ResponseEntity<>("Not active.Activating it.",HttpStatus.NOT_FOUND);
        }
        customer.get().setActive(true);
        customerRepository.save(customer.get());
        return new ResponseEntity<>("Customer is Active",HttpStatus.OK);
    }


//    API to de-activate a customer
@PutMapping("/admin/deactivate/customer/{id}")
public ResponseEntity<?> DeActivateCustomer(@PathVariable Long id) {
    Optional<CUSTOMER> customer = customerRepository.findById(id);
    if (customer.isPresent() == false) {    //checking validity
        return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }
    if(!customer.get().isActive()){
        return new ResponseEntity<>("Not active.Activating it.",HttpStatus.NOT_FOUND);
    }
    customer.get().setActive(false);
    customerRepository.save(customer.get());
    return new ResponseEntity<>("Customer is De-Active",HttpStatus.OK);
}

//    API to activate a seller
@PutMapping("/admin/activate/seller/{id}")
public ResponseEntity<?> ActivateSeller(@PathVariable Long id) {
    Optional<SELLER> seller = sellerRepository.findById(id);
    if (seller.isPresent() == false) {  //validating id
        return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }

    if(!seller.get().isActive()){   //Checking if active.
        return new ResponseEntity<>("Not active.Activating it.",HttpStatus.NOT_FOUND);
    }

    seller.get().setActive(true);
    sellerRepository.save(seller.get());
    return new ResponseEntity<>("Seller is Active",HttpStatus.OK);
}

//    API to de-activate a seller
@PutMapping("/admin/Deactivate/seller/{id}")
public ResponseEntity<?> DeActivateSeller(@PathVariable Long id) {
    Optional<SELLER> seller = sellerRepository.findById(id);
    if (seller.isPresent() == false) {
        return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }

    if(!seller.get().isActive()){   //Checking if active.
        return new ResponseEntity<>("Not active.Activating it.",HttpStatus.NOT_FOUND);
    }
    seller.get().setActive(false);
    sellerRepository.save(seller.get());
    return new ResponseEntity<>("Customer is Active",HttpStatus.OK);
}
}


