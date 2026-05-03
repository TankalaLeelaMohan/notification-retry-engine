# Event-Driven Notification & Retry Engine

## 🚀 Overview

This project is a backend system that processes notifications using a scheduler with retry logic, failure handling, and audit logging.

---

## 🔥 Features

* Scheduler-based processing using Spring Boot
* Retry mechanism with configurable `maxRetries`
* Dead-letter handling (DEAD state)
* Audit logging for every processing attempt
* Bulk notification API support
* Structured logging using SLF4J

---

## 🛠️ Tech Stack

* Java
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Scheduler (`@Scheduled`)
* SLF4J Logging

---

## 🔄 Workflow

1. Notification is created → `PENDING`
2. Scheduler picks eligible notifications
3. Status → `PROCESSING`
4. If success → `SUCCESS`
5. If failure → `FAILED` (retry scheduled)
6. If retries exceed limit → `DEAD`
7. Each attempt is logged in `NotificationLog`

---

## 📌 APIs

### Create Notification

`POST /notifications`

### Bulk Create

`POST /notifications/bulk`

### Get All Notifications

`GET /notifications`

### Get Logs by Notification ID

`GET /notifications/{id}/logs`

---

## 🧪 Sample Request (Bulk)

```json
[
  { "message": "Send Email" },
  { "message": "Send SMS" }
]
```

---

## ▶️ How to Run

1. Clone the repository
2. Run the Spring Boot application
3. Use Postman to test APIs
4. Observe scheduler processing and retry behavior

---

## 📊 Key Concepts Implemented

* Retry mechanism with backoff strategy
* Scheduler-based job processing
* Audit logging for traceability
* Separation of concerns (Controller → Service → Repository)

---

## 💡 Future Improvements

* Kafka-based event-driven architecture
* Distributed processing
* UI dashboard for monitoring
