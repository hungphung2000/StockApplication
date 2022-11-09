package com.example.stockapplication.exception;

import org.springframework.web.client.RestClientException;

import javax.naming.AuthenticationException;
import java.net.Authenticator;

public class UsernameNotFoundException extends AuthenticationException {
    public UsernameNotFoundException(String msg) {
        super(msg);
    }
}
