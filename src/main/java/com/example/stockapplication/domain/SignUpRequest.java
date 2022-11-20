package com.example.stockapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class SignUpRequest {
    @NotNull(message = "CCCD is required!")
    private String cccd;

    @NotNull(message = "FirstName is required!")
    private String firstName;

    @NotNull(message = "LastName is required!")
    private String lastName;

    @Email(message = "Email should be valid!")
    private String email;

    @NotNull(message = "Username should be valid!")
    private String username;

    @Size(min = 8, message = "Password must be at least 8 characters!")
    private String password;
}
