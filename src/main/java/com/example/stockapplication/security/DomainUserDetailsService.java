package com.example.stockapplication.security;

import com.example.stockapplication.entity.User;
import com.example.stockapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        userOptional.orElseThrow(() -> new UsernameNotFoundException("User with user " + username + " was not found in the database"));

        User user = userOptional.get();
        return new DomainUserDetails(user);
    }

    public UserDetails loadUserById(int userId) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));

        User user = userOptional.get();
        return new DomainUserDetails(user);
    }
}
