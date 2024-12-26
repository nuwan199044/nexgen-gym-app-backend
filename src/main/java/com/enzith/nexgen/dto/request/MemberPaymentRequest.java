package com.enzith.nexgen.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberPaymentRequest {

    @JsonProperty("member_membership_id")
    private Long memberMembershipId;

    @JsonProperty("member_id")
    private Long memberId;

    @JsonProperty("installment_id")
    private Long installmentId;
}
