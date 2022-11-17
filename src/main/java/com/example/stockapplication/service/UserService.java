package com.example.stockapplication.service;

import com.example.stockapplication.domain.SignUpRequest;
import com.example.stockapplication.domain.UserDTO;
import com.example.stockapplication.entity.User;
import com.example.stockapplication.enums.EnumRole;
import com.example.stockapplication.exception.AccessRepositoryException;
import com.example.stockapplication.exception.UserAlreadyExistsException;
import com.example.stockapplication.exception.UserNotFoundException;
import com.example.stockapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        String username = signUpRequest.getUsername();
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException(username);
        }

        User user = new User(signUpRequest);
        user.setRole(EnumRole.USER);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("SERVER ERROR");
            throw new AccessRepositoryException("HAVE ERROR");
        }
    }

    public void updateUser(int userId, UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.orElseThrow(() -> new UserNotFoundException(userId));

        User user = userOptional.get();
        user.setCccd(userDTO.getCccd());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setLastUpdated(LocalDateTime.now());

        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("GG DO NOT SAVE USER");
            throw new AccessRepositoryException("ERROR");
        }
    }

    public UserDTO getUser(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.orElseThrow(() -> new UserNotFoundException(userId));

        return userOptional.get().userDTO();
    }

    public List<UserDTO> getUsers() {
        return userRepository.findAllUsers();
    }
}
