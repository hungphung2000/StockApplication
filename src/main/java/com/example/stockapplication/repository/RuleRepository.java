package com.example.stockapplication.repository;

import com.example.stockapplication.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RuleRepository extends JpaRepository<Rule, UUID> {
    @Query(value = "SELECT r.classified FROM Rule r " +
                    "WHERE r.profitability = :profitability " +
                    "AND r.activity = :activity " +
                    "AND r.liquidity = :liquidity " +
                    "AND r.debt = :debt " +
                    "AND r.market = :market")
    List<String> findByIndexs(@Param("profitability") boolean profitability,
                              @Param("activity") boolean activity,
                              @Param("liquidity") boolean liquidity,
                              @Param("debt") boolean debt,
                              @Param("market") boolean market);
}
