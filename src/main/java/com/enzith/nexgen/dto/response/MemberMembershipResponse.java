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
public class MemberMembershipResponse {

    @JsonProperty("member_membership_id")
    private Long memberMembershipId;

    @JsonProperty("membership_type_id")
    private Long membershipTypeId;

    @JsonProperty("membership_type")
    private MembershipTypeResponse membershipType;

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

    @JsonProperty("installments")
    private List<InstallmentResponse> installments;
}
