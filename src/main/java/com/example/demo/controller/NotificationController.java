package com.example.demo.controller;

import com.example.demo.entity.NotificationLog;
import com.example.demo.service.NotificationService;
import com.example.demo.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService service;

    @GetMapping("/")
    public String greet(){
        return "Welcome to Event Driven Notification System";
    }

    @GetMapping("/notification")
    public List<Notification> getAllNotification(){
        return service.getAllNotification();
    }

    @GetMapping("/notification/{id}")
    public Notification getNotificationById(@PathVariable Long id){
        return service.getNotificationById(id);
    }

    @PostMapping("/addNotification")
    public void addNotification(@RequestBody List<Notification> notification){
        service.addNotification(notification);
    }

    @GetMapping("/notifications/{id}/logs")
    public List<NotificationLog> getLogs(@PathVariable Long id) {
        return service.getLogs(id);
    }

    @PostMapping("/notification/process/{id}")
    public Notification processNotification(@PathVariable Long id){
        return service.processNotification(id);
    }
}
