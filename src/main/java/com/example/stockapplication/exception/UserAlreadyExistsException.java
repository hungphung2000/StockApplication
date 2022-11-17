package com.example.stockapplication.exception;

import org.springframework.web.client.RestClientException;

public class UserAlreadyExistsException extends RestClientException {
    public UserAlreadyExistsException(String username) {
        super(username);
    }

    public UserAlreadyExistsException(int  userId) {
        super(String.format("User already exists with user_id: %d", userId));
    }
}
