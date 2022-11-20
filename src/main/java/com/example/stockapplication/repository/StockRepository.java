package com.example.stockapplication.repository;

import com.example.stockapplication.domain.StockDTO;
import com.example.stockapplication.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Integer> {
    @Query("SELECT new com.example.stockapplication.domain.StockDTO(s.id, s.stockSymbol, s.netProfit, s.totalAssets, s.revenue, s.currentAssets, s.currentDebt, s.totalLiabilities, s.stockPrice, s.eps, s.createdDate) " +
            "FROM Stock s WHERE s.createdDate >= :date")
    List<StockDTO> findByCreatedDate(@Param("date") LocalDateTime date);

    @Query("SELECT new com.example.stockapplication.domain.StockDTO(s.id, s.stockSymbol, s.netProfit, s.totalAssets, s.revenue, s.currentAssets, s.currentDebt, s.totalLiabilities, s.stockPrice, s.eps, s.createdDate) " +
            "FROM Stock s WHERE s.stockSymbol LIKE :stockSymbol " +
            "AND s.createdDate <= :endDate AND s.createdDate >= :startDate")
    List<StockDTO> findByStockSymbolAndTimeBetween(@Param("stockSymbol") String stockSymbol,
                                                @Param("startDate") LocalDateTime startDate,
                                                @Param("endDate") LocalDateTime endDate);

    @Query("SELECT s.stockPrice FROM Stock s WHERE s.stockSymbol = :stockSymbol " +
            "AND DATE(s.createdDate) BETWEEN :startDate AND :endDate")
    List<Float> findStockPriceByStockSymBolAndDate(@Param("stockSymbol") String stockSymbol,
                                                       @Param("startDate") LocalDate startDate,
                                                       @Param("endDate") LocalDate endDate);

    @Query("SELECT count(s) FROM Stock s WHERE s.stockSymbol = :stockSymbol AND s.createdDate >= :date")
    int countByStockSymbolAndCreatedDate(@Param("stockSymbol") String stockSymbol,
                                         @Param("date") LocalDateTime date);
}
