package com.enzith.nexgen.dto.response;

import com.enzith.nexgen.enums.PaymentMode;
import com.enzith.nexgen.enums.PaymentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberPaymentResponse {

    @JsonProperty("payment_id")
    private Long paymentId;

    @JsonProperty("payment_no")
    private String paymentNo;

    @JsonProperty("member_id")
    @JsonBackReference
    private MemberResponse member;

    @JsonProperty("payment_type")
    private PaymentType paymentType;

    @JsonProperty("payment_amount")
    private Double paymentAmount;

    @JsonProperty("payment_date")
    private LocalDate paymentDate;

    @JsonProperty("installment_id")
    private InstallmentResponse installment;

    @JsonProperty("payment_mode")
    private PaymentMode paymentMode;

    @JsonProperty("member_membership_id")
    private MemberMembershipResponse memberMembership;

    @JsonProperty("member_trainer_session_id")
    private MemberTrainerSessionResponse memberTrainerSession;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("created_by")
    private String createdBy;

}
