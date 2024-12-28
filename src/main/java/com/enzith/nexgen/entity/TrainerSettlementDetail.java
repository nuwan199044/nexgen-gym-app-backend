package com.enzith.nexgen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trainer_settlement_details")
public class TrainerSettlementDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "settlement_detail_id")
    private Long settlementDetailId;

    @Column(name = "description")
    private String description;

    @Column(name = "session_count")
    private Integer sessionCount;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "settlement_id", referencedColumnName = "settlement_id", nullable = false)
    private TrainerSettlement settlementId;
}
