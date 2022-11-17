package com.example.stockapplication.controller;

import com.example.stockapplication.domain.LoginRequest;
import com.example.stockapplication.domain.LoginResponse;
import com.example.stockapplication.domain.SignUpRequest;
import com.example.stockapplication.domain.StockDTO;
import com.example.stockapplication.security.DomainUserDetails;
import com.example.stockapplication.security.TokenProvider;
import com.example.stockapplication.service.StockService;
import com.example.stockapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://127.0.0.1:4200", maxAge = 3600)
@RequestMapping("/app")
public class AppController {
    private final UserService userService;

    private final StockService stockService;

    private final AuthenticationManager authManager;

    private final TokenProvider tokenProvider;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignUpRequest signUpRequest) {
        userService.addUser(signUpRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/user/buy-stock/{userId}/{stockId}")
    public ResponseEntity<Void> buyStock(@PathVariable("userId") int userId,
                                         @PathVariable("stockId") int stockId) {
        stockService.processBuyStock(userId, stockId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            DomainUserDetails userDetails = (DomainUserDetails) authentication.getPrincipal();
            String accessToken = tokenProvider.generateAccessToken(userDetails);

            return ResponseEntity.ok(new LoginResponse(userDetails.getId(), accessToken));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/user/add-stock-buy-out/{userId}")
    public ResponseEntity<Void> buyStock(@PathVariable("userId") int userId,
                                         @RequestBody @Valid StockDTO stockDTO)  {
        stockService.buyStockOut(userId, stockDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
