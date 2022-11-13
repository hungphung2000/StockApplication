package com.example.stockapplication.exception;

import org.springframework.web.client.RestClientException;

public class UserAlreadyExistsException extends RestClientException {
    public UserAlreadyExistsException(String username) {
        super(username);
    }
}
