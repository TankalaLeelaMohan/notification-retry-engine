package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Notification;
import com.example.demo.Status;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    @Query("""
    SELECT n FROM Notification n
    WHERE 
        n.status = :pendingStatus
        OR (
            n.status = :failedStatus
            AND n.currentRetries < n.maxRetries
            AND n.nextRetryTime <= :now
        )
""")
    List<Notification> getEligibleNotifications(
            @Param("now") LocalDateTime now,
            @Param("pendingStatus") Status pendingStatus,
            @Param("failedStatus") Status failedStatus
    );


}
