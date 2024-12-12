package com.enzith.nexgen.dto.response;

import com.enzith.nexgen.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrainerResponse {

    @JsonProperty("trainer_id")
    private Long trainerId;

    @JsonProperty("trainer_no")
    private String trainerNo;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty("phone_no_1")
    private String phoneNo1;

    @JsonProperty("phone_no_2")
    private String phoneNo2;

    @JsonProperty("permanent_address")
    private String permanentAddress;

    @JsonProperty("temporary_address")
    private String temporaryAddress;

    @JsonProperty("photo")
    private String photo;

    @JsonProperty("status")
    private Status status;

}
