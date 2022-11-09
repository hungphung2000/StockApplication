package com.example.stockapplication.exception;

import org.springframework.web.client.RestClientException;

public class AccessRepositoryException extends RestClientException {
    public AccessRepositoryException(String msg) {
        super(msg);
    }
}
