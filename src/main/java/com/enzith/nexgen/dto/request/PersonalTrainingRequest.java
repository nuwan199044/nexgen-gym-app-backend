package com.enzith.nexgen.dto.request;

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
public class PersonalTrainingRequest {

    @JsonProperty("personal_training_id")
    private Long personalTrainingId;

    @JsonProperty("from_time")
    private LocalDateTime fromTime;

    @JsonProperty("to_time")
    private LocalDateTime toTime;

}
