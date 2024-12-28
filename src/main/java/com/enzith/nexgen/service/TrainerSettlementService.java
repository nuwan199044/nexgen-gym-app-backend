package com.enzith.nexgen.service;

import com.enzith.nexgen.dto.response.TrainerSettlementResponse;

import java.time.LocalDate;

public interface TrainerSettlementService {
    TrainerSettlementResponse getTrainerSettlement(LocalDate fromDate, LocalDate toDate, Long trainerId);
    TrainerSettlementResponse trainerSettlement(LocalDate fromDate, LocalDate toDate, Long trainerId);
}
