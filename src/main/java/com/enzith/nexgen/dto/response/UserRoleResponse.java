package com.enzith.nexgen.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRoleResponse {
    @JsonProperty("user_role_id")
    private Integer userRoleId;

    @JsonProperty("user_role_name")
    private String userRoleName;
}
