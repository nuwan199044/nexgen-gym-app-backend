package com.enzith.nexgen.service.impl;

import com.enzith.nexgen.dto.response.TrainerResponse;
import com.enzith.nexgen.dto.response.TrainerSettlementDetailResponse;
import com.enzith.nexgen.dto.response.TrainerSettlementResponse;
import com.enzith.nexgen.entity.MemberTrainerSession;
import com.enzith.nexgen.entity.Trainer;
import com.enzith.nexgen.entity.TrainerSettlement;
import com.enzith.nexgen.entity.TrainerSettlementDetail;
import com.enzith.nexgen.enums.PaymentStatus;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.enums.SettlementStatus;
import com.enzith.nexgen.exception.TrainerException;
import com.enzith.nexgen.repository.MemberTrainerSessionRepository;
import com.enzith.nexgen.repository.TrainerRepository;
import com.enzith.nexgen.repository.TrainerSettlementDetailRepository;
import com.enzith.nexgen.repository.TrainerSettlementRepository;
import com.enzith.nexgen.service.TrainerSettlementService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.enzith.nexgen.common.AppConstants.OTHER_SESSIONS;
import static com.enzith.nexgen.common.AppConstants.SINGLE_SESSIONS;

@Service
@RequiredArgsConstructor
public class TrainerSettlementServiceImpl implements TrainerSettlementService {

    private final TrainerRepository trainerRepository;
    private final MemberTrainerSessionRepository memberTrainerSessionRepository;
    private final TrainerSettlementRepository trainerSettlementRepository;
    private final TrainerSettlementDetailRepository trainerSettlementDetailRepository;
    private final ModelMapper modelMapper;

    @Override
    public TrainerSettlementResponse getTrainerSettlement(LocalDate fromDate, LocalDate toDate, Long trainerId) {
        return buildTrainerSettlementResponse(fromDate, toDate, trainerId, false);
    }

    @Override
    public TrainerSettlementResponse trainerSettlement(LocalDate fromDate, LocalDate toDate, Long trainerId) {
        return buildTrainerSettlementResponse(fromDate, toDate, trainerId, true);
    }

    private TrainerSettlementResponse buildTrainerSettlementResponse(
            LocalDate fromDate, LocalDate toDate, Long trainerId, boolean updateSettlement) {

        Trainer trainer = validateTrainer(trainerId);

        List<MemberTrainerSession> allSessions = memberTrainerSessionRepository
                .findByTrainerAndPurchaseDateBetweenAndSettlementStatusAndPaymentStatus(trainer, fromDate, toDate, SettlementStatus.PENDING, PaymentStatus.PAID);

        Map<Boolean, List<MemberTrainerSession>> sessionsByType = allSessions.stream()
                .collect(Collectors.partitioningBy(session -> session.getTrainerPackage().getSessionCount() == 1));

        List<MemberTrainerSession> singleSessions = sessionsByType.get(true);
        List<MemberTrainerSession> otherSessions = sessionsByType.get(false);

        double singleSessionAmount = calculateTotalAmount(singleSessions);
        double otherSessionAmount = calculateTotalAmount(otherSessions);

        List<TrainerSettlementDetailResponse> detailResponses = List.of(
                getTrainerSettlementDetailResponse(SINGLE_SESSIONS, singleSessions.size(), singleSessionAmount),
                getTrainerSettlementDetailResponse(OTHER_SESSIONS, otherSessions.size(), otherSessionAmount)
        );

        double totalAmount = singleSessionAmount + otherSessionAmount;
        double totalPayment = calculateTotalPayment(trainer, singleSessionAmount, otherSessionAmount, totalAmount);

        if (updateSettlement) {
            allSessions.forEach(session -> session.setSettlementStatus(SettlementStatus.SETTLED));
            memberTrainerSessionRepository.saveAll(allSessions);

            TrainerSettlement trainerSettlement = trainerSettlementRepository.save(getTrainerSettlement(trainer, fromDate, toDate, totalAmount, totalPayment, allSessions.size()));
            trainerSettlementDetailRepository.saveAll(getTrainerSettlementDetails(trainerSettlement, detailResponses));
        }

        return TrainerSettlementResponse.builder()
                .settlementDate(LocalDate.now())
                .trainer(modelMapper.map(trainer, TrainerResponse.class))
                .fromDate(fromDate)
                .toDate(toDate)
                .totalSessionCount(allSessions.size())
                .totalAmount(totalAmount)
                .totalPayment(totalPayment)
                .status(updateSettlement ? SettlementStatus.SETTLED.name() : SettlementStatus.PENDING.name())
                .settlementDetails(detailResponses)
                .build();
    }

    private double calculateTotalAmount(List<MemberTrainerSession> sessions) {
        return sessions.stream()
                .mapToDouble(MemberTrainerSession::getNetAmount)
                .sum();
    }

    private double calculateTotalPayment(Trainer trainer, double singleSessionAmount, double otherSessionAmount, double totalAmount) {
        return trainer.getIsBasicPay()
                ? (singleSessionAmount * 0.5) + (otherSessionAmount * 0.4)
                : totalAmount * 0.5;
    }

    private Trainer validateTrainer(Long trainerId) {
        return trainerRepository.findById(trainerId)
                .orElseThrow(() -> new TrainerException(ResponseCode.TRAINER_NOT_FOUND));
    }

    private TrainerSettlementDetailResponse getTrainerSettlementDetailResponse(String description, int value, double amount) {
        return TrainerSettlementDetailResponse.builder()
                .description(description)
                .sessionCount(value)
                .amount(amount)
                .build();
    }

    private TrainerSettlement getTrainerSettlement(Trainer trainer, LocalDate fromDate, LocalDate toDate, double totalAmount, double totalPayment, int totalSessionCount) {
        return TrainerSettlement.builder()
                .settlementDate(LocalDate.now())
                .trainer(trainer)
                .fromDate(fromDate)
                .toDate(toDate)
                .totalSessionCount(totalSessionCount)
                .totalAmount(totalAmount)
                .totalPayment(totalPayment)
                .build();
    }

    private List<TrainerSettlementDetail> getTrainerSettlementDetails(TrainerSettlement trainerSettlement,
                                                               List<TrainerSettlementDetailResponse> trainerSettlementDetails) {
        return trainerSettlementDetails.stream()
                .map(trainerSettlementDetailResponse ->
                        TrainerSettlementDetail.builder().settlementId(trainerSettlement)
                                .description(trainerSettlementDetailResponse.getDescription())
                                .sessionCount(trainerSettlementDetailResponse.getSessionCount())
                                .amount(trainerSettlementDetailResponse.getAmount()).build())
                .toList();
    }

}
