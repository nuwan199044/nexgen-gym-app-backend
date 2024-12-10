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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberMembershipRequest {

    @JsonProperty("member_membership_id")
    private Long memberMembershipId;

    @JsonProperty("member_id")
    private Long memberId;
    
    @JsonProperty("membership_type_id")
    private Long membershipTypeId;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    @JsonProperty("total_amount")
    private Double totalAmount;

    @JsonProperty("discount")
    private Double discount;

    @JsonProperty("net_amount")
    private Double netAmount;

    @JsonProperty("payment_status")
    private String paymentStatus;

    @JsonProperty("status")
    private String status;

    @JsonProperty("is_paid_in_installments")
    private Boolean isPaidInInstallments;

    @JsonProperty("installment_count")
    private Integer installmentCount;

}
