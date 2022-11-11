package com.example.stockapplication.service;

import com.example.stockapplication.domain.SignUpRequest;
import com.example.stockapplication.entity.User;
import com.example.stockapplication.exception.AccessRepositoryException;
import com.example.stockapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public int findUserByUsername(String username) {
        return userRepository.findByUsername(username).get().getId();
    }

    @Transactional
    public void addUser(SignUpRequest signUpRequest) {
        User user = new User(signUpRequest);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("SERVER ERROR");
            throw new AccessRepositoryException("HAVE ERROR");
        }
    }
}
