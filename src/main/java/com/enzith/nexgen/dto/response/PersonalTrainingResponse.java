package com.enzith.nexgen.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonalTrainingResponse {

    @JsonProperty("personal_training_id")
    private Long personalTrainingId;

    @JsonProperty("session_no")
    private Integer sessionNo;

    @JsonProperty("member_id")
    private Long memberId;

    @JsonProperty("trainer_id")
    private Long trainerId;

    @JsonProperty("package_id")
    private Long trainerPackageId;

    @JsonProperty("from_time")
    private LocalDateTime fromTime;

    @JsonProperty("to_time")
    private LocalDateTime toTime;

    @JsonProperty("status")
    private String status;

    @JsonProperty("complete_status")
    private String completeStatus;

}
