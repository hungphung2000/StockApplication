package com.example.stockapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class StockDTO {
    private int id;

    @NotNull(message = "Field is required!")
    private String stockSymbol;

    @NotNull(message = "Field is required!")
    private float netProfit;

    @NotNull(message = "Field is required!")
    private float totalAssets;

    @NotNull(message = "Field is required!")
    private float revenue;

    @NotNull(message = "Field is required!")
    private float currentAssets;

    @NotNull(message = "Field is required!")
    private float currentDebt;

    @NotNull(message = "Field is required!")
    private float totalLiabilities;

    @NotNull(message = "Field is required!")
    private float stockPrice;

    @NotNull(message = "Field is required!")
    private float eps;

    private LocalDateTime createdDate;

    private String label;
}
