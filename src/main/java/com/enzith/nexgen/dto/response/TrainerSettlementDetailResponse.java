package com.enzith.nexgen.dto.response;

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
public class TrainerSettlementDetailResponse {

    @JsonProperty("settlement_detail_id")
    private Long settlementDetailId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("session_count")
    private Integer sessionCount;

    @JsonProperty("amount")
    private Double amount;
}
