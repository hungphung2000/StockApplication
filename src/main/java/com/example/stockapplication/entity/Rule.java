package com.example.stockapplication.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "rule")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "rule_no")
    private String ruleNo;

    @Column(name = "profitability")
    private boolean profitability;

    @Column(name = "activity")
    private boolean activity;

    @Column(name = "liquidity")
    private boolean liquidity;

    @Column(name = "debt")
    private boolean debt;

    @Column(name = "market")
    private boolean market;

    @Column(name = "classified")
    private String classified;
}
