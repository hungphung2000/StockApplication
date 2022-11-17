package com.example.stockapplication.exception;

import org.springframework.web.client.RestClientException;

public class UserStockLikeNotFoundException extends RestClientException {
    public UserStockLikeNotFoundException(String msg) {
        super(msg);
    }
}
