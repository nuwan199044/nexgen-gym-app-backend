package com.enzith.nexgen.repository;

import com.enzith.nexgen.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NotificationRepository extends JpaSpecificationExecutor<Notification>, PagingAndSortingRepository<Notification, Long>,
        JpaRepository<Notification, Long> {
}
