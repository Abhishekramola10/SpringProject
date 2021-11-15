package com.BootCampProject1.BootCampProject1.repository;

import com.BootCampProject1.BootCampProject1.users.CUSTOMER;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CUSTOMER, Long> {
    Optional<CUSTOMER> findByEmail(String username);
}
