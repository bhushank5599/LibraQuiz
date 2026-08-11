package com.lq.notification.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long recipientUserId;

    private Long userId;
    private String recipientEmail;

    private String title;
    private String subject;

    @Column(length = 4000)
    private String message;

    @Column(length = 4000)
    private String body;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType = NotificationType.IN_APP;

    @Enumerated(EnumType.STRING)
    private NotificationType type = NotificationType.IN_APP;

    private boolean isRead = false;
    private boolean read = false;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status = NotificationStatus.SENT;

    private LocalDateTime createdAt;
    private LocalDateTime sentAt;

    public Notification() {
    }

    public Notification(Long id, Long recipientUserId, Long userId, String recipientEmail, String title, String subject, String message, String body, NotificationType notificationType, NotificationType type, boolean isRead, boolean read, NotificationStatus status, LocalDateTime createdAt, LocalDateTime sentAt) {
        this.id = id;
        this.recipientUserId = recipientUserId != null ? recipientUserId : userId;
        this.userId = userId != null ? userId : recipientUserId;
        this.recipientEmail = recipientEmail;
        this.title = title != null ? title : subject;
        this.subject = subject != null ? subject : title;
        this.message = message != null ? message : body;
        this.body = body != null ? body : message;
        this.notificationType = notificationType != null ? notificationType : type;
        this.type = type != null ? type : notificationType;
        this.isRead = isRead || read;
        this.read = read || isRead;
        if (status != null) this.status = status;
        this.createdAt = createdAt;
        this.sentAt = sentAt;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.sentAt == null) {
            this.sentAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecipientUserId() {
        return recipientUserId != null ? recipientUserId : userId;
    }

    public void setRecipientUserId(Long recipientUserId) {
        this.recipientUserId = recipientUserId;
        this.userId = recipientUserId;
    }

    public Long getUserId() {
        return userId != null ? userId : recipientUserId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
        this.recipientUserId = userId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getTitle() {
        return title != null ? title : subject;
    }

    public void setTitle(String title) {
        this.title = title;
        this.subject = title;
    }

    public String getSubject() {
        return subject != null ? subject : title;
    }

    public void setSubject(String subject) {
        this.subject = subject;
        this.title = subject;
    }

    public String getMessage() {
        return message != null ? message : body;
    }

    public void setMessage(String message) {
        this.message = message;
        this.body = message;
    }

    public String getBody() {
        return body != null ? body : message;
    }

    public void setBody(String body) {
        this.body = body;
        this.message = body;
    }

    public NotificationType getNotificationType() {
        return notificationType != null ? notificationType : type;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
        this.type = notificationType;
    }

    public NotificationType getType() {
        return type != null ? type : notificationType;
    }

    public void setType(NotificationType type) {
        this.type = type;
        this.notificationType = type;
    }

    public boolean isRead() {
        return isRead || read;
    }

    public boolean getIsRead() {
        return isRead || read;
    }

    public void setRead(boolean read) {
        this.read = read;
        this.isRead = read;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
        this.read = isRead;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public enum NotificationType {
        EMAIL, SMS, IN_APP, SYSTEM, GENERAL
    }

    public enum NotificationStatus {
        PENDING, SENT, FAILED
    }

    public static NotificationBuilder builder() {
        return new NotificationBuilder();
    }

    public static class NotificationBuilder {
        private Long id;
        private Long recipientUserId;
        private Long userId;
        private String recipientEmail;
        private String title;
        private String subject;
        private String message;
        private String body;
        private NotificationType notificationType = NotificationType.IN_APP;
        private NotificationType type = NotificationType.IN_APP;
        private boolean isRead = false;
        private boolean read = false;
        private NotificationStatus status = NotificationStatus.SENT;
        private LocalDateTime createdAt;
        private LocalDateTime sentAt;

        public NotificationBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public NotificationBuilder recipientUserId(Long recipientUserId) {
            this.recipientUserId = recipientUserId;
            this.userId = recipientUserId;
            return this;
        }

        public NotificationBuilder userId(Long userId) {
            this.userId = userId;
            this.recipientUserId = userId;
            return this;
        }

        public NotificationBuilder recipientEmail(String recipientEmail) {
            this.recipientEmail = recipientEmail;
            return this;
        }

        public NotificationBuilder title(String title) {
            this.title = title;
            this.subject = title;
            return this;
        }

        public NotificationBuilder subject(String subject) {
            this.subject = subject;
            this.title = subject;
            return this;
        }

        public NotificationBuilder message(String message) {
            this.message = message;
            this.body = message;
            return this;
        }

        public NotificationBuilder body(String body) {
            this.body = body;
            this.message = body;
            return this;
        }

        public NotificationBuilder notificationType(NotificationType notificationType) {
            this.notificationType = notificationType;
            this.type = notificationType;
            return this;
        }

        public NotificationBuilder type(NotificationType type) {
            this.type = type;
            this.notificationType = type;
            return this;
        }

        public NotificationBuilder isRead(boolean isRead) {
            this.isRead = isRead;
            this.read = isRead;
            return this;
        }

        public NotificationBuilder read(boolean read) {
            this.read = read;
            this.isRead = read;
            return this;
        }

        public NotificationBuilder status(NotificationStatus status) {
            this.status = status;
            return this;
        }

        public NotificationBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public NotificationBuilder sentAt(LocalDateTime sentAt) {
            this.sentAt = sentAt;
            return this;
        }

        public Notification build() {
            return new Notification(id, recipientUserId, userId, recipientEmail, title, subject, message, body, notificationType, type, isRead, read, status, createdAt, sentAt);
        }
    }
}
