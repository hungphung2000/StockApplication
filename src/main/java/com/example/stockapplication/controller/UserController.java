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

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping("{userId}/liked-stocks")
    public ResponseEntity<List<StockDTO>> getStocks(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(stockService.getFavoriteStocks(userId));
    }

    @PostMapping("{userId}/favorite-stocks/{stockId}")
    public ResponseEntity<Void> addFavoriteStock(@PathVariable("userId") int userId,
                                         @PathVariable("stockId") int stockId) {
        stockService.addFavoriteStock(userId, stockId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}/bought-stocks")
    public ResponseEntity<List<StockDTO>> getBoughtStocks(@PathVariable("userId") int userId) {
        return ResponseEntity.ok(stockService.getBoughtStocks(userId));
    }

    @DeleteMapping("{userId}/stocks-like/{stockId}")
    public ResponseEntity<Void> deleteLikedStock(@PathVariable("userId") int userId,
                                                 @PathVariable("stockId") int stockId) {
        stockService.deleteLikedStock(userId, stockId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/stocks-bought/{stockId}")
    public ResponseEntity<Void> deleteBoughtStock(@PathVariable("userId") int userId,
                                                 @PathVariable("stockId") int stockId) {
        stockService.deleteBoughtStock(userId, stockId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{userId}/buy-stock/{stockId}")
    public ResponseEntity<Void> buyStock(@PathVariable("userId") int userId,
                                         @PathVariable("stockId") int stockId) {
        stockService.processBuyStock(userId, stockId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
