package com.enzith.nexgen.repository;

import com.enzith.nexgen.entity.TrainerSettlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TrainerSettlementRepository extends JpaSpecificationExecutor<TrainerSettlement>,
        PagingAndSortingRepository<TrainerSettlement, Long>, JpaRepository<TrainerSettlement, Long> {
}