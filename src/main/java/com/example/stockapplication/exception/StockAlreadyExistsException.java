package com.example.stockapplication.exception;

import org.springframework.web.client.RestClientException;

public class StockAlreadyExistsException extends RestClientException {
    public StockAlreadyExistsException(String msg) {
        super(msg);
    }
}
