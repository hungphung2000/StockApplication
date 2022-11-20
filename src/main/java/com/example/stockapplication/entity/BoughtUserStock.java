package com.example.stockapplication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bought_user_stock")
public class BoughtUserStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name = "price")
    private float price;

    @Column(name = "stock_name")
    private String stockName;

    @Column(name = "bought_at")
    LocalDateTime boughtAt;

    public BoughtUserStock(User user, Stock stock, float price, String stockName) {
        this.user = user;
        this.stock = stock;
        this.price = price;
        this.stockName = stockName;
        this.boughtAt = LocalDateTime.now();
    }
}
