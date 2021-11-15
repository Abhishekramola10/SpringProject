package com.BootCampProject1.security_class;

import com.BootCampProject1.BootCampProject1.filter.JwtRequestFilter;
import com.BootCampProject1.BootCampProject1.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(myUserDetailsService);
    }

    @Autowired
    public JwtRequestFilter jwtRequestFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/admin_user/login").permitAll()
                .antMatchers("/admin_user/logout").hasAnyRole("ADMIN")
                .antMatchers("/customer_user/login").permitAll()
                .antMatchers("/customer_user/logout").hasAnyRole("CUSTOMER","ADMIN")
                .antMatchers("/seller_user/login").permitAll()
                .antMatchers("/seller_user/logout").hasAnyRole("SELLER","ADMIN")
                .antMatchers("/users/register").permitAll()
                .antMatchers("/sellers/register").permitAll()
                .antMatchers("/activate_customer").permitAll()
                .antMatchers("/resend_activation_link").permitAll()
                //admin
                .antMatchers("/users/customers").hasAnyRole("ADMIN")
                .antMatchers("/users/sellers").hasAnyRole("ADMIN")
                .antMatchers("/admin/activate/customer/{id}").hasAnyRole("ADMIN")
                .antMatchers("/admin/deactivate/customer/{id}").hasAnyRole("ADMIN")
                .antMatchers("/admin/Deactivate/seller/{id}").hasAnyRole("ADMIN")
            //customer
                .antMatchers("/view/customer").hasAnyRole("CUSTOMER","ADMIN")
                .antMatchers("/view/customer/address").hasAnyRole("CUSTOMER","ADMIN")
                .antMatchers("/update/customer/profile").hasAnyRole("CUSTOMER","ADMIN")
                .antMatchers("/update/customer/password").hasAnyRole("CUSTOMER","ADMIN")
                .antMatchers("/add/customer/new_address").hasAnyRole("CUSTOMER","ADMIN")
                .antMatchers("/delete/customer/address/{id}").hasAnyRole("CUSTOMER","ADMIN")
                .antMatchers("/update/customer/address").hasAnyRole("CUSTOMER","ADMIN")
                .antMatchers("/ForgotPassword").hasAnyRole("CUSTOMER","ADMIN")
                .antMatchers("/ForgotToken").hasAnyRole("CUSTOMER","ADMIN")
                .antMatchers("/view/seller").hasAnyRole("SELLER","ADMIN")
                .antMatchers("/update/seller/profile").hasAnyRole("SELLER","ADMIN")
                .antMatchers("/update/seller/password").hasAnyRole("SELLER","ADMIN")
                .antMatchers("/update/seller/password").hasAnyRole("SELLER","ADMIN")
                .anyRequest().authenticated()
                .and().sessionManagement()  //spring do not create session we have our filter
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override   //For Error: consider defining a bean of AM
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return (PasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
