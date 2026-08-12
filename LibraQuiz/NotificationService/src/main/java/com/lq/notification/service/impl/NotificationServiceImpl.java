package com.lq.notification.service.impl;

import com.lq.notification.dto.NotificationResponse;
import com.lq.notification.dto.SendNotificationRequest;
import com.lq.notification.entity.Notification;
import com.lq.notification.repository.NotificationRepository;
import com.lq.notification.service.NotificationService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final JavaMailSender mailSender;

    public NotificationServiceImpl(NotificationRepository notificationRepository, Optional<JavaMailSender> mailSender) {
        this.notificationRepository = notificationRepository;
        this.mailSender = mailSender.orElse(null);
    }

    @Override
    @Transactional
    public NotificationResponse sendNotification(SendNotificationRequest request) {
        Notification notification = Notification.builder()
                .userId(request.getUserId())
                .recipientEmail(request.getRecipientEmail())
                .subject(request.getSubject())
                .body(request.getBody())
                .type(request.getType() != null ? request.getType() : Notification.NotificationType.SYSTEM)
                .status(Notification.NotificationStatus.SENT)
                .sentAt(LocalDateTime.now())
                .build();

        // Attempt live SMTP Mail Dispatch if JavaMailSender is configured
        if (mailSender != null && request.getRecipientEmail() != null && !request.getRecipientEmail().trim().isEmpty()) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(request.getRecipientEmail().trim());
                message.setSubject(request.getSubject() != null ? request.getSubject() : "Thank you for registering with LibraQuiz");
                message.setText(request.getBody());
                mailSender.send(message);
                System.out.println("✅ Live SMTP email dispatched to: " + request.getRecipientEmail());
            } catch (Exception e) {
                System.err.println("⚠️ SMTP Dispatch Note (Configure spring.mail in application.yml for SMTP delivery): " + e.getMessage());
            }
        }

        // Server-Side Direct Web Email Dispatch
        if (request.getRecipientEmail() != null && !request.getRecipientEmail().trim().isEmpty()) {
            dispatchServerWebMail(request.getRecipientEmail().trim(), request.getSubject(), request.getBody());
        }

        Notification saved = notificationRepository.save(notification);
        return toResponse(saved);
    }

    private void dispatchServerWebMail(String recipientEmail, String subject, String bodyText) {
        String safeSubject = subject != null ? subject : "Thank you for registering with LibraQuiz";
        String safeBody = bodyText != null ? bodyText.replace("\n", "\\n").replace("\"", "\\\"") : "";

        // Channel 1: Web3Forms Main Key
        try {
            java.net.URL url = new java.net.URL("https://api.web3forms.com/submit");
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            String jsonPayload = String.format(
                "{\"access_key\":\"232b6944-9388-4f81-807d-9442a4a75a74\",\"subject\":\"%s\",\"from_name\":\"LibraQuiz Administration\",\"to_email\":\"%s\",\"email\":\"%s\",\"message\":\"%s\"}",
                safeSubject, recipientEmail, recipientEmail, safeBody
            );

            try (java.io.OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(java.nio.charset.StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int code = conn.getResponseCode();
            System.out.println("📩 Backend NotificationService Primary Email Dispatch Code: " + code);
        } catch (Exception e) {
            System.err.println("⚠️ Primary Email Dispatch note: " + e.getMessage());
        }

        // Channel 2: Web3Forms Secondary Key
        try {
            java.net.URL url2 = new java.net.URL("https://api.web3forms.com/submit");
            java.net.HttpURLConnection conn2 = (java.net.HttpURLConnection) url2.openConnection();
            conn2.setRequestMethod("POST");
            conn2.setRequestProperty("Content-Type", "application/json");
            conn2.setRequestProperty("Accept", "application/json");
            conn2.setDoOutput(true);

            String jsonPayload2 = String.format(
                "{\"access_key\":\"40625024-f875-c946-fe16-b90cf02cc291\",\"subject\":\"%s\",\"from_name\":\"LibraQuiz Admin\",\"to\":\"%s\",\"email\":\"%s\",\"message\":\"%s\"}",
                safeSubject, recipientEmail, recipientEmail, safeBody
            );

            try (java.io.OutputStream os2 = conn2.getOutputStream()) {
                byte[] input2 = jsonPayload2.getBytes(java.nio.charset.StandardCharsets.UTF_8);
                os2.write(input2, 0, input2.length);
            }

            int code2 = conn2.getResponseCode();
            System.out.println("📩 Backend NotificationService Secondary Email Dispatch Code: " + code2);
        } catch (Exception e) {
            System.err.println("⚠️ Secondary Email Dispatch note: " + e.getMessage());
        }
    }

    @Override
    public List<NotificationResponse> getUserNotifications(Long userId) {
        return notificationRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private NotificationResponse toResponse(Notification n) {
        return NotificationResponse.builder()
                .id(n.getId())
                .userId(n.getUserId())
                .recipientEmail(n.getRecipientEmail())
                .subject(n.getSubject())
                .body(n.getBody())
                .type(n.getType())
                .status(n.getStatus())
                .sentAt(n.getSentAt())
                .build();
    }
}
