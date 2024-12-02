package com.enzith.nexgen.utility;

import com.enzith.nexgen.dto.APIResponse;
import com.enzith.nexgen.enums.ResponseCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class APIResponseUtil {

    public static <T> APIResponse<T> createResponse(ResponseCode response, T data) {
        APIResponse<T> apiResponse = new APIResponse<>();
        apiResponse.setResponseCode(response.getCode());
        apiResponse.setResponseMsg(response.getMessage());
        apiResponse.setData(data);
        return apiResponse;
    }

}
