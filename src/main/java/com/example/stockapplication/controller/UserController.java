package com.example.stockapplication.controller;

import com.example.stockapplication.domain.StockDTO;
import com.example.stockapplication.domain.UserDTO;
import com.example.stockapplication.service.StockService;
import com.example.stockapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://127.0.0.1:4200", maxAge = 3600)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final StockService stockService;

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("/liked-stocks/{userId}")
    public ResponseEntity<List<StockDTO>> getStocks(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(stockService.getFavoriteStocks(userId));
    }

    @PostMapping("/favorite-stock/{userId}/{stockId}")
    public ResponseEntity<Void> addFavoriteStock(@PathVariable("userId") int userId,
                                         @PathVariable("stockId") int stockId) {
        stockService.addFavoriteStock(userId, stockId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/bought-stocks/{userId}")
    public ResponseEntity<List<StockDTO>> getBoughtStocks(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(stockService.getBoughtStocks(userId));
    }

    @DeleteMapping("/stocks-like/delete/{userId}/{stockId}")
    public ResponseEntity<Void> deleteLikedStock(@PathVariable("userId") int userId,
                                                 @PathVariable("stockId") int stockId) {
        stockService.deleteLikedStock(userId, stockId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/stocks-bought/delete/{userId}/{stockId}")
    public ResponseEntity<Void> deleteBoughtStock(@PathVariable("userId") int userId,
                                                 @PathVariable("stockId") int stockId) {
        stockService.deleteBoughtStock(userId, stockId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
