package com.enzith.nexgen.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainerSettlementResponse {
    
    @JsonProperty("trainer_id")
    private TrainerResponse trainer;

    @JsonProperty("from_date")
    private LocalDate fromDate;

    @JsonProperty("to_date")
    private LocalDate toDate;

    @JsonProperty("total_session_count")
    private Integer totalSessionCount;

    @JsonProperty("total_amount")
    private Double totalAmount;

    @JsonProperty("total_payment")
    private Double totalPayment;

    @JsonProperty("settlement_date")
    private LocalDate settlementDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("settlement_details")
    List<TrainerSettlementDetailResponse> settlementDetails;
}
