package com.enzith.nexgen.repository;

import com.enzith.nexgen.entity.MemberTrainerSession;
import com.enzith.nexgen.entity.Trainer;
import com.enzith.nexgen.enums.PaymentStatus;
import com.enzith.nexgen.enums.SettlementStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface MemberTrainerSessionRepository extends JpaSpecificationExecutor<MemberTrainerSession>,
        PagingAndSortingRepository<MemberTrainerSession, Long>, JpaRepository<MemberTrainerSession, Long> {
    List<MemberTrainerSession> findByTrainerAndPurchaseDateBetweenAndSettlementStatusAndPaymentStatus(
            Trainer trainer,
            LocalDate fromDate,
            LocalDate toDate,
            SettlementStatus settlementStatus,
            PaymentStatus paymentStatus);

}
