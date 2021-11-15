package com.BootCampProject1.BootCampProject1.repository;

import com.BootCampProject1.BootCampProject1.users.USER;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Repository extends JpaRepository<USER,Long> {

    Optional<USER> findByEmail(String email);

}
