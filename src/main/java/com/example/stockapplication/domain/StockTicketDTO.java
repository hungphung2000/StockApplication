package com.example.stockapplication.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockTicketDTO {
    @NotBlank
    private String stockName;

    @NotBlank
    private String companyName;
}
