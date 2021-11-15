package com.BootCampProject1.BootCampProject1.repository;

import com.BootCampProject1.BootCampProject1.users.SELLER;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<SELLER, Long> {
    Optional<SELLER> findByEmail(String username);
}
