package com.example.stockapplication.entity;

import com.example.stockapplication.domain.SignUpRequest;
import com.example.stockapplication.domain.UserDTO;
import com.example.stockapplication.enums.EnumRole;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Column(name = "status")
    private boolean status;

    @OneToMany(mappedBy = "user")
    private List<UserStock> userStocks;

    @OneToMany(mappedBy = "user")
    private List<BoughtUserStock>boughtUserStocks;

    @Enumerated(EnumType.STRING)
    private EnumRole role;

    public User(SignUpRequest signUpRequest) {
        this.cccd = signUpRequest.getCccd();
        this.firstName = signUpRequest.getFirstName();
        this.lastName = signUpRequest.getLastName();
        this.email = signUpRequest.getEmail();
        this.username = signUpRequest.getUsername();
        this.password = signUpRequest.getPassword();
        this.createdDate = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
        this.status = true;
    }

    public UserDTO userDTO() {
        return UserDTO.builder()
                .cccd(this.cccd)
                .email(this.email)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .build();
    }
}
