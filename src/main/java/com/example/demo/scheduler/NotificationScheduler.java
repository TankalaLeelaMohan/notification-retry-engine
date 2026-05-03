package com.example.demo.scheduler;

import com.example.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    @Autowired
    NotificationService service;

    @Scheduled(fixedDelay = 10000)
    void runScheduler(){
        System.out.println("Scheduler Started");
        service.processEligibleNotifications();
    }

}
