package io.javabrains;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Component;

//import com.innovativeintelli.ldapauthenticationjwttoken.security.JwtTokenProvider;

@Component
public class jwtUtil {
	
	private static final String SECRET_KEY = "secret";
	private static final Logger logger = LoggerFactory.getLogger(jwtUtil.class);
	
    @Value("${app.jwtSecret}")
    private String jwtSecret;
	
	@Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;
	
	public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
	}
	
	public String generateToken(String username) {
    	//LdapUserDetailsImpl userPrincipal = (LdapUserDetailsImpl) authentication.getPrincipal();
    	
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        System.out.println(now + " " + expiryDate + " " + username + " " + jwtSecret);
        
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
	}
	
	public Boolean ValidateToken(String authToken) {
	
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        System.out.println("Some Issue");
               return false;
	}
	
}
