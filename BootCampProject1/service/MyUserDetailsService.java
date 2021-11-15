package com.BootCampProject1.BootCampProject1.service;

import com.BootCampProject1.BootCampProject1.users.USER;
import com.BootCampProject1.BootCampProject1.appuser.AppUser;
import com.BootCampProject1.BootCampProject1.appuser.GrantAuthorityImpl;
import com.BootCampProject1.BootCampProject1.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired

    private Repository repository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        return new User("username", "password", new ArrayList<>());
        USER user = repository.findByEmail(userName).get();//fetch value from db
        System.out.println(user);
        if (userName != null) {
            //new GrantAuthorityImpl(user.getRole())
            return new AppUser(user.getEmail(), user.getPassword(), Arrays.asList(new GrantAuthorityImpl("ROLE_ADMIN")));
        } else {
            throw new RuntimeException();
        }
    }
}
