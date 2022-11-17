package com.example.stockapplication.repository;

import com.example.stockapplication.entity.BoughtUserStock;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BoughtUserStockRepository extends JpaRepository<BoughtUserStock, Integer> {
    Optional<BoughtUserStock> findByUser_IdAndStock_Id(int userId, int stockId);

    List<BoughtUserStock> findByUser_Id(int userId);
}
