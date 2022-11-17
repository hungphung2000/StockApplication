package com.example.stockapplication.entity;

import com.example.stockapplication.domain.StockTicketDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock_ticket")
@Entity
public class StockTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "stock_name", unique = true)
    private String stockName;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Column(name = "company_name", unique = true)
    private String companyName;

    public StockTicket(StockTicketDTO stockTicketDTO) {
        this.stockName = stockTicketDTO.getStockName();
        this.companyName = stockTicketDTO.getCompanyName();
        createdDate = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();
    }
}
