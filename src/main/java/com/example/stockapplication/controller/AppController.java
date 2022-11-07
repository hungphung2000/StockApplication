package com.example.stockapplication.controller;

import com.example.stockapplication.domain.SignUpRequest;
import com.example.stockapplication.service.StockService;
import com.example.stockapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/app")
public class AppController {
    private final UserService userService;

    private final StockService stockService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signup(@RequestBody SignUpRequest signUpRequest) {
        userService.addUser(signUpRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/user/buy-stock/{userId}/{stockId}")
    public ResponseEntity<Void> buyStock(@PathVariable("userId") int userId,
                                         @PathVariable("stockId") int stockId) {
        stockService.processBuyStock(userId, stockId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
