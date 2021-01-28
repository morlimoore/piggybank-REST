package com.morlimoore.piggybankapi.util;

import com.morlimoore.piggybankapi.entities.User;
import com.morlimoore.piggybankapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    @Autowired
    UserRepository userRepository;

    public User getAuthenticatedUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        return userRepository.findUserByEmail(username).get();
    }
}
