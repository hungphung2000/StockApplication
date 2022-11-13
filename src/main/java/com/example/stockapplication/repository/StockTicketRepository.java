package com.example.stockapplication.repository;

import com.example.stockapplication.entity.StockTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StockTicketRepository extends JpaRepository<StockTicket, Integer> {
    @Query("SELECT s.stockName FROM StockTicket s")
    List<String> findAllStockName();

    Optional<StockTicket> findByStockName(String stockName);
}
