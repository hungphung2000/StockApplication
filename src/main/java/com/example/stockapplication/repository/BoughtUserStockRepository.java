package com.example.stockapplication.repository;

import com.example.stockapplication.entity.BoughtUserStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoughtUserStockRepository extends JpaRepository<BoughtUserStock, UUID> {
    Optional<BoughtUserStock> findByUser_IdAndStock_Id(UUID userId, UUID stockId);

    List<BoughtUserStock> findByUser_Id(UUID userId);
}
