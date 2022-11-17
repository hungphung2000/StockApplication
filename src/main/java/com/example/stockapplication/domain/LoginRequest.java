package com.example.stockapplication.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "Username is not blank!")
    String username;

    @NotBlank(message = "Password is not blank!")
    String password;
}
