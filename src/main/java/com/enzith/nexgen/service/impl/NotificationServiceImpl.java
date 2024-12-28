package com.enzith.nexgen.service.impl;

import com.enzith.nexgen.criteria.NotificationCriteria;
import com.enzith.nexgen.criteria.PaginationCriteria;
import com.enzith.nexgen.dto.response.NotificationResponse;
import com.enzith.nexgen.entity.Notification;
import com.enzith.nexgen.enums.NotificationStatus;
import com.enzith.nexgen.enums.ResponseCode;
import com.enzith.nexgen.exception.NotificationException;
import com.enzith.nexgen.repository.NotificationRepository;
import com.enzith.nexgen.service.NotificationService;
import com.enzith.nexgen.specification.NotificationSpecification;
import com.enzith.nexgen.utility.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final ModelMapper modelMapper;

    @Override
    public NotificationResponse createNotification(Notification notification) {
        return modelMapper.map(notificationRepository.save(notification), NotificationResponse.class);
    }

    @Override
    public Map<String, Object> findAllNotifications(String status, Integer currentPage, Integer pageSize) {
        NotificationCriteria criteria = NotificationCriteria.builder()
                .status(status)
                .build();

        PaginationCriteria paginationCriteria = PaginationCriteria.builder()
                .currentPage(currentPage)
                .pageSize(pageSize)
                .build();

        Pageable pageable = PaginationUtils.getPage(paginationCriteria);
        Page<Notification> notifications = notificationRepository.findAll(new NotificationSpecification(criteria), pageable);
        return PaginationUtils.convertToPagination(notifications.map(notification -> modelMapper.map(notification, NotificationResponse.class)));
    }

    @Override
    public NotificationResponse seenNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationException(ResponseCode.NOTIFICATION_NOT_FOUND));

        notification.setStatus(NotificationStatus.SEEN);
        notification.setSeenAt(LocalDateTime.now());
        return modelMapper.map(notificationRepository.save(notification), NotificationResponse.class);
    }
}
