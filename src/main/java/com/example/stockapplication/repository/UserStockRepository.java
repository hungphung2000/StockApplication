package com.example.stockapplication.repository;

import com.example.stockapplication.entity.Stock;
import com.example.stockapplication.entity.UserStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserStockRepository extends JpaRepository<UserStock, Integer> {
    Optional<UserStock> findByUser_IdAndStock_Id(int userId, int stockId);

    List<Stock> findByUser_Id(int userId);
}
