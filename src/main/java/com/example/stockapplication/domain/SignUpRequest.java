package com.example.stockapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SignUpRequest {
    private String cccd;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String sex;

    private String address;

    private String phoneNumber;

    private String email;

    private String username;

    private String password;
}
