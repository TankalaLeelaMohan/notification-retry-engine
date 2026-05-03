package com.example.demo.service;

import com.example.demo.Status;
import com.example.demo.entity.Notification;
import com.example.demo.entity.NotificationLog;
import com.example.demo.repository.LogRepository;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.Status;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
@Service
public class NotificationService {

    public static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private NotificationRepository repository;

    @Autowired
    private LogRepository logRepository;

    public void addNotification(List<Notification> notification) {
        for(Notification n : notification)
        {
            n.setStatus(Status.PENDING);
            n.setCurrentRetries(0);
            n.setCreatedAt(LocalDateTime.now());
            n.setFailureReason(null);
            repository.save(n);
        }
    }

    public List<Notification> getAllNotification() {
        return repository.findAll();
    }

    public Notification getNotificationById(Long id) {
        return repository.findById(id).orElse(new Notification());
    }

    public Notification processNotification(Long id) {
        Notification notification = repository.findById(id).
                orElseThrow(() -> new RuntimeException("Notification not found"));

        Random random = new Random();

        boolean isSuccess = random.nextBoolean();

        String[] errors = {
                "SMTP Timeout",
                "Third-party API Failure",
                "Network Issue"
        };


        if(isSuccess)
        {
            notification.setStatus(Status.SUCCESS);
            notification.setFailureReason(null);
        }
        else
        {
            notification.setCurrentRetries(notification.getCurrentRetries()+1);
            //notification.setFailureReason(errors[random.nextInt(errors.length)]);
            notification.setFailureReason(errors[random.nextInt(errors.length)]);
            if(notification.getCurrentRetries()>= notification.getMaxRetries()){
                notification.setStatus(Status.DEAD);
            }
            else{
                notification.setStatus(Status.FAILED);
                notification.setNextRetryTime(LocalDateTime.now().plusMinutes(1));
            }
        }
        logger.info("Process of id {} is in {} status " , notification.getId() , notification.getStatus());

        NotificationLog notificationLog = new NotificationLog();
        notificationLog.setNotificationId(notification.getId());
        notificationLog.setStatus(notification.getStatus());
        notificationLog.setRetryCount(notification.getCurrentRetries());
        notificationLog.setTimestamp(LocalDateTime.now());
        logRepository.save(notificationLog);
        return repository.save(notification);
    }

    public void processEligibleNotifications() {
        logger.info("processEligibleNotifications Started");
        List<Notification> list = repository.getEligibleNotifications(LocalDateTime.now(),Status.PENDING,Status.FAILED);
        logger.info("List Size {}" , list.size());
        for(Notification n : list){
           logger.info("Process of id {} started" , n.getId());
            n.setStatus(Status.PROCESSING);
            processNotification(n.getId());
            //logger.info("Process of id {} is in {} status " , n.getId() , n.getStatus());
        }
    }

    public List<NotificationLog> getLogs(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Notification not found");
        }
        return logRepository.findByNotificationIdOrderByTimestampAsc(id);
    }
}
