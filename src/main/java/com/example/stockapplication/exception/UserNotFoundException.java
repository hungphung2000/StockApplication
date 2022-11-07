package com.example.stockapplication.exception;

import org.springframework.web.client.RestClientException;

import java.util.UUID;

public class UserNotFoundException extends RestClientException {
    public UserNotFoundException(UUID userId) {
        super(String.format("User not found with userId: '%s'", userId));
    }
}
