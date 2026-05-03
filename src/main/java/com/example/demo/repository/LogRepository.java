package com.example.demo.repository;

import com.example.demo.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<NotificationLog,Long> {
    List<NotificationLog> findByNotificationIdOrderByTimestampAsc(Long notificationId);
}