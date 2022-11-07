package com.example.stockapplication.repository;

import com.example.stockapplication.entity.UserStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStockRepository extends JpaRepository<UserStock, UUID> {
    Optional<UserStock> findByUser_IdAndStock_Id(UUID userId, UUID stockId);

    List<UserStock> findByUser_Id(UUID userId);
}
