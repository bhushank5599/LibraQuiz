package com.lq.notification.dto;

import com.lq.notification.entity.Notification;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SendNotificationRequest {

    @NotNull(message = "Recipient User ID is required")
    private Long recipientUserId;

    private Long userId;
    private String recipientEmail;

    private String title;

    @NotBlank(message = "Subject/Title is required")
    private String subject;

    private String message;

    @NotBlank(message = "Message/Body is required")
    private String body;

    private Notification.NotificationType notificationType;
    private Notification.NotificationType type;

    public SendNotificationRequest() {
    }

    public SendNotificationRequest(Long recipientUserId, Long userId, String recipientEmail, String title, String subject, String message, String body, Notification.NotificationType notificationType, Notification.NotificationType type) {
        this.recipientUserId = recipientUserId != null ? recipientUserId : userId;
        this.userId = userId != null ? userId : recipientUserId;
        this.recipientEmail = recipientEmail;
        this.title = title != null ? title : subject;
        this.subject = subject != null ? subject : title;
        this.message = message != null ? message : body;
        this.body = body != null ? body : message;
        this.notificationType = notificationType != null ? notificationType : type;
        this.type = type != null ? type : notificationType;
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

    public static SendNotificationRequestBuilder builder() {
        return new SendNotificationRequestBuilder();
    }

    public static class SendNotificationRequestBuilder {
        private Long recipientUserId;
        private Long userId;
        private String recipientEmail;
        private String title;
        private String subject;
        private String message;
        private String body;
        private Notification.NotificationType notificationType;
        private Notification.NotificationType type;

        public SendNotificationRequestBuilder recipientUserId(Long recipientUserId) {
            this.recipientUserId = recipientUserId;
            this.userId = recipientUserId;
            return this;
        }

        public SendNotificationRequestBuilder userId(Long userId) {
            this.userId = userId;
            this.recipientUserId = userId;
            return this;
        }

        public SendNotificationRequestBuilder recipientEmail(String recipientEmail) {
            this.recipientEmail = recipientEmail;
            return this;
        }

        public SendNotificationRequestBuilder title(String title) {
            this.title = title;
            this.subject = title;
            return this;
        }

        public SendNotificationRequestBuilder subject(String subject) {
            this.subject = subject;
            this.title = subject;
            return this;
        }

        public SendNotificationRequestBuilder message(String message) {
            this.message = message;
            this.body = message;
            return this;
        }

        public SendNotificationRequestBuilder body(String body) {
            this.body = body;
            this.message = body;
            return this;
        }

        public SendNotificationRequestBuilder notificationType(Notification.NotificationType notificationType) {
            this.notificationType = notificationType;
            this.type = notificationType;
            return this;
        }

        public SendNotificationRequestBuilder type(Notification.NotificationType type) {
            this.type = type;
            this.notificationType = type;
            return this;
        }

        public SendNotificationRequest build() {
            return new SendNotificationRequest(recipientUserId, userId, recipientEmail, title, subject, message, body, notificationType, type);
        }
    }
}
