package com.example.stockapplication.exception;

import org.springframework.web.client.RestClientException;

import java.util.UUID;

public class StockNotFoundException extends RestClientException {
    public StockNotFoundException(int stockId) {
        super(String.format("Stock not found with stockId: '%s'", stockId));
    }
}
