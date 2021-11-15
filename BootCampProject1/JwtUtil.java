package com.BootCampProject1.BootCampProject1;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String SECRET_KEY ="secret";

    public String extractUsername(String token){    //4
        return extractClaim(token, Claims::getSubject);
    }
    
    public Date extractExpiration(String token){    //5
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {  //3
        final Claims claims = extractAllClaims(token);  //figuring out what claims are
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){   //6
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails){   //1
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject){  //2
      return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*60*10))
              .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
  }

    public Boolean validateToken(String token, UserDetails userDetails){ //7
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
