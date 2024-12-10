package com.enzith.nexgen.dto.response;

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
public class InstallmentResponse {

    @JsonProperty("installment_id")
    private Long installmentId;

    @JsonProperty("installment_amount")
    private Double installmentAmount;

    @JsonProperty("due_date")
    private LocalDate dueDate;

    @JsonProperty("installment_no")
    private Integer installmentNo;

    @JsonProperty("payment_status")
    private String paymentStatus;

    @JsonProperty("status")
    private String status;

}
