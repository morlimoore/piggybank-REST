package com.morlimoore.piggybankapi.config;

import com.morlimoore.piggybankapi.security.JwtAuthenticationFilter;
import com.morlimoore.piggybankapi.security.JwtTokenProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenFilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtTokenProvider tokenProvider;

    public JwtTokenFilterConfig(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }
    @Override
    public void configure(HttpSecurity builder) {
        // create a new filter and add to the builder.
        JwtAuthenticationFilter customFilter = new JwtAuthenticationFilter(tokenProvider);
        builder.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
