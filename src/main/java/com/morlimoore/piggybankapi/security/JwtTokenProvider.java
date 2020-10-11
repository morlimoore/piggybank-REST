package com.morlimoore.piggybankapi.security;

import com.morlimoore.piggybankapi.exceptions.CustomException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@PropertySource("classpath:application-dev.yml")
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${application.jwt.secret-key}")
    private String secretKey;

    @Value("${application.jwt.login-expire-length}")
    private long loginValidityInMilliseconds;

    @Value("${application.jwt.reset-password-expire-length}")
    private long passwordResetValidityInMilliseconds;

    UserDetailsService userDetailsService;

    public JwtTokenProvider(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    protected String generateToken(String subject, Long validityPeriod) {
        Claims claims = Jwts.claims().setSubject(subject);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityPeriod);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createLoginToken(String username) {
        return generateToken(username, loginValidityInMilliseconds);
    }

    public String createForgotPasswordToken(String username) {
        return generateToken(username, passwordResetValidityInMilliseconds);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        // this also checks if the token is expired
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
            throw new CustomException("Invalid JWT signature", HttpStatus.FORBIDDEN);
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
            throw new CustomException("Invalid JWT token", HttpStatus.FORBIDDEN);
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
            throw new CustomException("Expired JWT token", HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
            throw new CustomException("Unsupported JWT token", HttpStatus.FORBIDDEN);
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
            throw new CustomException("JWT claims string is empty.", HttpStatus.FORBIDDEN);
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        return getAllClaims(token)
                .getExpiration()
                .before(new Date());
    }
}