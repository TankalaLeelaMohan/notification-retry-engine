package com.example.demo.entity;

import com.example.demo.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class NotificationLog {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private Long notificationId;
        @Enumerated(EnumType.STRING)
        private Status status;
        private int retryCount;
        private String message;
        private LocalDateTime timestamp;
}
