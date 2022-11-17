package com.example.stockapplication.exception;

import org.springframework.web.client.RestClientException;

public class UserNotFoundException extends RestClientException {
    public UserNotFoundException(int userId) {
        super(String.format("User not found with userId: '%s'", userId));
    }

    public UserNotFoundException(String username) {
        super(String.format("User not found with userId: '%s'", username));
    }
}
