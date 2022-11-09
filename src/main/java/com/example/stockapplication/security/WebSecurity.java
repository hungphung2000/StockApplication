package com.example.stockapplication.security;

import com.example.stockapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {
    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;

    private final DomainUserDetailsService userDetailsService;

    public static String[] PUBLIC_ENDPOINTS = {
            "/app/login"
    };

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http)
            throws Exception {
            return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder())
                .and()
                .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(PUBLIC_ENDPOINTS).permitAll()
                .antMatchers("/app/**").permitAll()
                .antMatchers("/stocks/**").permitAll()
                .antMatchers("/users/**").permitAll();
        http.addFilterBefore(new JwtTokenFilter(tokenProvider, userDetailsService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
