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
public class NotificationResponse {

    @JsonProperty("notification_id")
    private Long notificationId;

    @JsonProperty("message")
    private String message;

    @JsonProperty("type")
    private String type;

    @JsonProperty("seen_at")
    private LocalDateTime seenAt;

    @JsonProperty("status")
    private String status;

}
