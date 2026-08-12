package com.lq.notification.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notification_templates")
public class NotificationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String templateCode;

    private String subject;

    @Column(length = 4000)
    private String bodyTemplate;

    public NotificationTemplate() {
    }

    public NotificationTemplate(Long id, String templateCode, String subject, String bodyTemplate) {
        this.id = id;
        this.templateCode = templateCode;
        this.subject = subject;
        this.bodyTemplate = bodyTemplate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBodyTemplate() {
        return bodyTemplate;
    }

    public void setBodyTemplate(String bodyTemplate) {
        this.bodyTemplate = bodyTemplate;
    }

    public static NotificationTemplateBuilder builder() {
        return new NotificationTemplateBuilder();
    }

    public static class NotificationTemplateBuilder {
        private Long id;
        private String templateCode;
        private String subject;
        private String bodyTemplate;

        public NotificationTemplateBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public NotificationTemplateBuilder templateCode(String templateCode) {
            this.templateCode = templateCode;
            return this;
        }

        public NotificationTemplateBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public NotificationTemplateBuilder bodyTemplate(String bodyTemplate) {
            this.bodyTemplate = bodyTemplate;
            return this;
        }

        public NotificationTemplate build() {
            return new NotificationTemplate(id, templateCode, subject, bodyTemplate);
        }
    }
}
