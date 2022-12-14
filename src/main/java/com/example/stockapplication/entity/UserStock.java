package com.example.stockapplication.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_stock")
public class UserStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name = "registered_at")
    LocalDateTime registeredAt;

    public UserStock(User user, Stock stock) {
        this.user = user;
        this.stock = stock;
        this.registeredAt = LocalDateTime.now();
    }
}
