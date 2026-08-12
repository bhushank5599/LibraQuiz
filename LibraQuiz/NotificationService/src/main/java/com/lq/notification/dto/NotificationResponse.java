package com.lq.notification.dto;

import com.lq.notification.entity.Notification;

import java.time.LocalDateTime;

public class NotificationResponse {

    private Long id;
    private Long recipientUserId;
    private Long userId;
    private String recipientEmail;
    private String title;
    private String subject;
    private String message;
    private String body;
    private Notification.NotificationType notificationType;
    private Notification.NotificationType type;
    private boolean isRead;
    private boolean read;
    private Notification.NotificationStatus status;
    private LocalDateTime sentAt;
    private LocalDateTime createdAt;

    public NotificationResponse() {
    }

    public NotificationResponse(Long id, Long recipientUserId, Long userId, String recipientEmail, String title, String subject, String message, String body, Notification.NotificationType notificationType, Notification.NotificationType type, boolean isRead, boolean read, Notification.NotificationStatus status, LocalDateTime sentAt, LocalDateTime createdAt) {
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
        this.status = status;
        this.sentAt = sentAt != null ? sentAt : createdAt;
        this.createdAt = createdAt != null ? createdAt : sentAt;
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

    public Notification.NotificationType getNotificationType() {
        return notificationType != null ? notificationType : type;
    }

    public void setNotificationType(Notification.NotificationType notificationType) {
        this.notificationType = notificationType;
        this.type = notificationType;
    }

    public Notification.NotificationType getType() {
        return type != null ? type : notificationType;
    }

    public void setType(Notification.NotificationType type) {
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

    public Notification.NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(Notification.NotificationStatus status) {
        this.status = status;
    }

    public LocalDateTime getSentAt() {
        return sentAt != null ? sentAt : createdAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
        this.createdAt = sentAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt != null ? createdAt : sentAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        this.sentAt = createdAt;
    }

    public static NotificationResponseBuilder builder() {
        return new NotificationResponseBuilder();
    }

    public static class NotificationResponseBuilder {
        private Long id;
        private Long recipientUserId;
        private Long userId;
        private String recipientEmail;
        private String title;
        private String subject;
        private String message;
        private String body;
        private Notification.NotificationType notificationType;
        private Notification.NotificationType type;
        private boolean isRead;
        private boolean read;
        private Notification.NotificationStatus status;
        private LocalDateTime sentAt;
        private LocalDateTime createdAt;

        public NotificationResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public NotificationResponseBuilder recipientUserId(Long recipientUserId) {
            this.recipientUserId = recipientUserId;
            this.userId = recipientUserId;
            return this;
        }

        public NotificationResponseBuilder userId(Long userId) {
            this.userId = userId;
            this.recipientUserId = userId;
            return this;
        }

        public NotificationResponseBuilder recipientEmail(String recipientEmail) {
            this.recipientEmail = recipientEmail;
            return this;
        }

        public NotificationResponseBuilder title(String title) {
            this.title = title;
            this.subject = title;
            return this;
        }

        public NotificationResponseBuilder subject(String subject) {
            this.subject = subject;
            this.title = subject;
            return this;
        }

        public NotificationResponseBuilder message(String message) {
            this.message = message;
            this.body = message;
            return this;
        }

        public NotificationResponseBuilder body(String body) {
            this.body = body;
            this.message = body;
            return this;
        }

        public NotificationResponseBuilder notificationType(Notification.NotificationType notificationType) {
            this.notificationType = notificationType;
            this.type = notificationType;
            return this;
        }

        public NotificationResponseBuilder type(Notification.NotificationType type) {
            this.type = type;
            this.notificationType = type;
            return this;
        }

        public NotificationResponseBuilder isRead(boolean isRead) {
            this.isRead = isRead;
            this.read = isRead;
            return this;
        }

        public NotificationResponseBuilder read(boolean read) {
            this.read = read;
            this.isRead = read;
            return this;
        }

        public NotificationResponseBuilder status(Notification.NotificationStatus status) {
            this.status = status;
            return this;
        }

        public NotificationResponseBuilder sentAt(LocalDateTime sentAt) {
            this.sentAt = sentAt;
            this.createdAt = sentAt;
            return this;
        }

        public NotificationResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            this.sentAt = createdAt;
            return this;
        }

        public NotificationResponse build() {
            return new NotificationResponse(id, recipientUserId, userId, recipientEmail, title, subject, message, body, notificationType, type, isRead, read, status, sentAt, createdAt);
        }
    }
}
