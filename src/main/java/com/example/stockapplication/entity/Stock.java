package com.example.stockapplication.entity;

import com.example.stockapplication.domain.StockDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "stock_symbol")
    private String stockSymbol;

    @Column(name = "net_profit")
    private float netProfit;

    @Column(name = "total_assets")
    private float totalAssets;

    @Column(name = "revenue")
    private float revenue;

    @Column(name = "current_assets")
    private float currentAssets;

    @Column(name = "current_debt")
    private float currentDebt;

    @Column(name = "total_liabilities")
    private float totalLiabilities;

    @Column(name = "stock_price")
    private float stockPrice;

    @Column(name = "eps")
    private float eps;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @ManyToOne
    @JoinColumn(name = "stock_ticket_id")
    private StockTicket stockTicket;

    public Stock(StockDTO stockDTO) {
        stockSymbol = stockDTO.getStockSymbol();
        netProfit = stockDTO.getNetProfit();
        totalAssets = stockDTO.getTotalAssets();
        revenue = stockDTO.getRevenue();
        currentAssets = stockDTO.getCurrentAssets();
        currentDebt = stockDTO.getCurrentDebt();
        totalLiabilities = stockDTO.getTotalLiabilities();
        stockPrice = stockDTO.getStockPrice();
        eps = stockDTO.getEps();
        createdDate = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();
    }

    public StockDTO stockDTO() {
        return StockDTO.builder()
                .id(this.id)
                .stockSymbol(this.stockSymbol)
                .netProfit(this.netProfit)
                .totalAssets(this.totalAssets)
                .revenue(this.revenue)
                .currentAssets(this.currentAssets)
                .currentDebt(this.currentDebt)
                .totalLiabilities(this.totalLiabilities)
                .stockPrice(this.stockPrice)
                .eps(this.eps)
                .createdDate(this.createdDate)
                .build();
    }
}
