package com.enzith.nexgen.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileDTO {

    @JsonProperty("user_profile_id")
    private Long userProfileId;

    @JsonProperty("address")
    private String address;

    @JsonProperty("contact_no")
    private String contactNo;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    @JsonProperty("email")
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("is_active")
    private Integer isActive;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("nic")
    private String nic;

    @JsonProperty("password")
    private String password;

    @JsonProperty("user_role_id")
    private Integer userRoleId;

}
