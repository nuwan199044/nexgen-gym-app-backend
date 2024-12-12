package com.enzith.nexgen.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberTrainerSessionRequest {

    @JsonProperty("member_trainer_session_id")
    private Long memberTrainerSessionId;

    @JsonProperty("member_id")
    private Long memberId;

    @JsonProperty("trainer_id")
    private Long trainerId;

    @JsonProperty("package_id")
    private Long packageId;

    @JsonProperty("purchase_date")
    private LocalDate purchaseDate;

    @JsonProperty("remaining_sessions")
    private Integer remainingSessions;

    @JsonProperty("total_amount")
    private Double totalAmount;

    @JsonProperty("net_amount")
    private Double netAmount;

    @JsonProperty("discount")
    private Double discount;

    @JsonProperty("payment_status")
    private String paymentStatus;
}
