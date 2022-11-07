package com.example.stockapplication.controller;

import com.example.stockapplication.domain.StockDTO;
import com.example.stockapplication.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;

    @GetMapping()
    public ResponseEntity<List<StockDTO>> filterStocks(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(stockService.getStocksByDate(date));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<StockDTO>> getStocks() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }

    @PostMapping("/add-stock")
    public ResponseEntity<Void> addStock(@RequestBody StockDTO stockDTO) {
        stockService.addStock(stockDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update-stock/{stockId}")
    public ResponseEntity<Void> updateStock(@PathVariable("stockId") UUID stockId,
                                            @RequestBody StockDTO stockDTO) {
        stockService.updateStock(stockId, stockDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
