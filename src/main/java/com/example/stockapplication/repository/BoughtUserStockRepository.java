package com.example.stockapplication.repository;

import com.example.stockapplication.entity.BoughtUserStock;
import com.example.stockapplication.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoughtUserStockRepository extends JpaRepository<BoughtUserStock, Integer> {
    Optional<BoughtUserStock> findByUser_IdAndStock_Id(int userId, int stockId);

    @Query("SELECT b.stock FROM BoughtUserStock b WHERE b.user.id = :userId")
    List<Stock> findByUser_Id(@Param("userId") int userId);
}
