package com.enzith.nexgen.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MembershipTypeRequest {

    @JsonProperty("membership_type_id")
    private Long membershipTypeId;

    @JsonProperty("membership_type_name")
    private String membershipTypeName;

    @JsonProperty("is_installments_allowed")
    private Boolean isInstallmentsAllowed;

    @JsonProperty("membership_fee")
    private Double membershipFee;

    @JsonProperty("duration_in_days")
    private Integer durationInDays;
}