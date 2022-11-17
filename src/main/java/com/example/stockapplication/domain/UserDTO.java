package com.example.stockapplication.domain;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String cccd;

    private String email;

    private String firstName;

    private String lastName;

    private LocalDateTime createdDate;

    private LocalDateTime lastUpdated;
}
