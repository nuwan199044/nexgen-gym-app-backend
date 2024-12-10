package com.enzith.nexgen.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIResponse<T> {

    @JsonProperty("response_code")
    private Integer responseCode;

    @JsonProperty("response_msg")
    private String responseMsg;

    @JsonProperty("data")
    private T data;
}
