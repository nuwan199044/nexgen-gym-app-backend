package com.enzith.nexgen.controller;

import com.enzith.nexgen.dto.request.MemberRequest;
import com.enzith.nexgen.dto.response.APIResponse;
import com.enzith.nexgen.dto.response.NotificationResponse;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.service.NotificationService;
import com.enzith.nexgen.utility.APIResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAllNotifications(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "current_page") Integer currentPage,
            @RequestParam(value = "page_size") Integer pageSize) {
        log.info("Received request to fetch all notification with filters - Status: {} Current Page: {}, Page Size: {}",
                status, currentPage, pageSize);
        return new ResponseEntity<>(notificationService.findAllNotifications(status, currentPage, pageSize), HttpStatus.OK);
    }

    @PutMapping("/{notification-id}/seen")
    public ResponseEntity<APIResponse<NotificationResponse>> seenNotification(@PathVariable("notification-id") Long notificationId) {
        return new ResponseEntity<>(
                APIResponseUtil.createResponse(
                        ResponseCode.NOTIFICATION_SEEN_SUCCESS,
                        notificationService.seenNotification(notificationId)
                ), HttpStatus.OK);
    }
}
