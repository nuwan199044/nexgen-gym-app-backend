package com.enzith.nexgen.dto.response;

import com.enzith.nexgen.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {

    @JsonProperty("member_id")
    private Long memberId;

    @JsonProperty("membership_no")
    private String membershipNo;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty("age")
    private Integer age;

    @JsonProperty("phone_no")
    private String phoneNo;

    @JsonProperty("nic")
    private String nic;

    @JsonProperty("address")
    private String address;

    @JsonProperty("email")
    private String email;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("join_date")
    private LocalDate joinDate;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("created_by")
    private String createdBy;

    @JsonProperty("member_membership")
    private List<MemberMembershipResponse> memberMemberships;

    @JsonProperty("installment")
    private List<InstallmentResponse> installments;
}
