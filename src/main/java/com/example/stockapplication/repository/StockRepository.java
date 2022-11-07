package com.example.stockapplication.repository;

import com.example.stockapplication.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, Integer> {
    @Query("SELECT s FROM Stock s WHERE s.createdDate >= ?1")
    List<Stock> findByCreatedDate(LocalDateTime date);
}
