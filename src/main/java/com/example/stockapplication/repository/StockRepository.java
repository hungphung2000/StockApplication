package com.example.stockapplication.repository;

import com.example.stockapplication.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Integer> {
    @Query("SELECT s FROM Stock s WHERE s.createdDate >= ?1")
    List<Stock> findByCreatedDate(LocalDateTime date);

    @Query("SELECT s FROM Stock s WHERE s.stockSymbol LIKE '%:stockSymbol' " +
            "AND s.createdDate BETWEEN :endDate AND :startDate")
    List<Stock> findByStockSymbolAndTimeBetween(@Param("stockSymbol") String stockSymbol,
                                                @Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);

    @Query("SELECT s.stockPrice FROM Stock s WHERE s.stockSymbol = :stockSymbol " +
            "AND DATE(s.createdDate) BETWEEN :startDate AND :endDate")
    List<Float> findStockPriceByStockSymBolAndDate(@Param("stockSymbol") String stockSymbol,
                                                       @Param("startDate") LocalDate startDate,
                                                       @Param("endDate") LocalDate endDate);
}
