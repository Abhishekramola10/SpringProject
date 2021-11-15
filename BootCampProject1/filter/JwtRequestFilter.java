package com.BootCampProject1.BootCampProject1.filter;

import com.BootCampProject1.BootCampProject1.JwtUtil;
import com.BootCampProject1.BootCampProject1.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public MyUserDetailsService userDetailsService;

    @Override //examines JWT from incoming request,if valid get the user-details out of
                //UserDetailsService and save it in the Security context.
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //bearer+JWT
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); //JWT
            username = jwtUtil.extractUsername(jwt);
        }

        //TO extract user detail
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        //user context should not have a authenticated user
            {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)) {  //validating JWT

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =  //what spring
                        new UsernamePasswordAuthenticationToken(                            //security
                                userDetails, null, userDetails.getAuthorities());   //do by
                usernamePasswordAuthenticationToken                                           //default
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response); //Handing off the controller to next filter in filter chain
    }
}

