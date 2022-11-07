package com.example.stockapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class StockDTO {
    private int id;

    private String stockSymbol;

        private float netProfit;

    private float totalAssets;

    private float revenue;

    private float currentAssets;

    private float currentDebt;

    private float totalLiabilities;

    private float stockPrice;

    private float eps;

    private LocalDateTime createdDate;

    private String label;
}
