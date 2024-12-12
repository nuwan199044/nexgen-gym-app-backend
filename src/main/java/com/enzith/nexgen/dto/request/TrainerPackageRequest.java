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
public class TrainerPackageRequest {

    @JsonProperty("package_id")
    private Long packageId;

    @JsonProperty("trainer_id")
    private Long trainerId;

    @JsonProperty("session_count")
    private Integer sessionCount;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("package_name")
    private String packageName;
}
