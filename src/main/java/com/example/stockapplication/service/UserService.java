package com.example.stockapplication.service;

import com.example.stockapplication.domain.SignUpRequest;
import com.example.stockapplication.domain.StockDTO;
import com.example.stockapplication.entity.BoughtUserStock;
import com.example.stockapplication.entity.Stock;
import com.example.stockapplication.entity.User;
import com.example.stockapplication.entity.UserStock;
import com.example.stockapplication.exception.StockNotFoundException;
import com.example.stockapplication.exception.UserNotFoundException;
import com.example.stockapplication.repository.BoughtUserStockRepository;
import com.example.stockapplication.repository.StockRepository;
import com.example.stockapplication.repository.UserRepository;
import com.example.stockapplication.repository.UserStockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public void addUser(SignUpRequest signUpRequest) {
        User user = new User(signUpRequest);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            log.error("SERVER ERROR");
        }
    }


}
