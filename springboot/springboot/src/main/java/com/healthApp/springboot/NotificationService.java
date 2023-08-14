package com.healthApp.springboot;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendNotificationToUser(String fcmToken, String title, String body) {
        Message message = Message.builder()
                .setToken(fcmToken)
                .putData("title", title)
                .putData("body", body)
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
            System.out.println("Notification sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error sending notification: " + e.getMessage());
        }
    }
}

