package com.healthApp.springboot;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class HealthAppService {
    @Autowired
    private NotificationService notificationService; // Inject the NotificationService
    public ResponseEntity<String> createPatient(@RequestBody Patient patient) throws InterruptedException, ExecutionException{
        Firestore dbFirestore = FirestoreClient.getFirestore();

        try {
            System.out.print(patient);
            ApiFuture<DocumentReference> apiFuture = dbFirestore.collection("healthApp").add(patient);

            // Wait for the operation to complete
            apiFuture.get();

            return new ResponseEntity<>("Record Inserted Successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Object> readPatient(@RequestParam String documentId) {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFireStore.collection("healthApp").document(documentId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                Patient patient = document.toObject(Patient.class);
                patient.setDocument_user_id(documentId);
                return new ResponseEntity<>(patient, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<String> deletePatient(@RequestParam String documentId){
        Firestore dbFireStore  = FirestoreClient .getFirestore();
        try {
            ApiFuture<WriteResult> collectionApiFuture = (ApiFuture<WriteResult>) dbFireStore.collection("healthApp").document(documentId).delete();

            return new ResponseEntity<>("Successfully Deleted Document Id: "+ documentId, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<List<Patient>> getAllPatients() {

        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference collectionRef = dbFirestore.collection("healthApp");

        try {
            ApiFuture<QuerySnapshot> querySnapshot = collectionRef.get();
            List<Patient> patients = new ArrayList<>();

            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                Patient patient = document.toObject(Patient.class);
                patient.setDocument_user_id(document.getId());
                patients.add(patient);
            }

            return new ResponseEntity<>(patients, HttpStatus.OK);
        }
        catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Return ResponseEntity with status and body
            }
    }
    public ResponseEntity<String> updateFcmToken(
            @RequestParam String userId,
            @RequestBody User user) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection("fcmToken").document(userId);

        try {
            System.out.println(documentReference);
            System.out.println("FCM Token" + user.fcmToken);
            System.out.println("User Id" + userId);
            // Get the current DocumentSnapshot
            ApiFuture<DocumentSnapshot> future = documentReference.get();
            DocumentSnapshot document = future.get();

            // Check if the document exists
            if (document.exists()) {
                // Get the existing User object from the DocumentSnapshot
//                User user = document.toObject(User.class);

                // Update the FCM token field using the update method
                ApiFuture<WriteResult> updateResult = documentReference.update("fcmToken", user.fcmToken);

                // Wait for the update operation to complete
                updateResult.get();

                return new ResponseEntity<>("FCM Token updated successfully", HttpStatus.OK);
            } else {
                Map<String, Object> data = new HashMap<>();
                data.put("fcmToken", user.fcmToken); // Store the FCM token directly as a string value

                ApiFuture<WriteResult> apiFuture = dbFirestore.collection("fcmToken")
                        .document(user.getUserId())
                        .set(user); // Set the document data using the map

                // Wait for the operation to complete
                apiFuture.get();

                return new ResponseEntity<>("Record Inserted Successfully", HttpStatus.CREATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> storeFCMToken(@RequestBody User user) throws InterruptedException, ExecutionException{
        Firestore dbFirestore = FirestoreClient.getFirestore();

        try {
            Map<String, Object> data = new HashMap<>();
            data.put("fcmToken", user.getFcmToken());

            ApiFuture<WriteResult> apiFuture = dbFirestore.collection("fcmToken")
                    .document(user.getUserId())
                    .set(data); // Set the document data using the map

            // Wait for the operation to complete
            apiFuture.get();

            return new ResponseEntity<>("Record Inserted Successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<List<User>> getAllFcmTokens() {

        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference collectionRef = dbFirestore.collection("fcmToken");

        try {
            ApiFuture<QuerySnapshot> querySnapshot = collectionRef.get();
            List<User> users = new ArrayList<>();

            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                User user = document.toObject(User.class);
                user.setUserId(document.getId());
                users.add(user);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Return ResponseEntity with status and body
        }
    }
    public ResponseEntity<Object> readFCMToken(@RequestParam String userId) {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFireStore.collection("fcmToken").document(userId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();

        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                User user = document.toObject(User.class);
                user.setUserId(userId);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @Scheduled(fixedRate = 60000) // Run every minute
//    public void scheduleNotifications() {
//        sendNotifications();
//    }

    private void sendNotifications() {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference collectionRef = dbFirestore.collection("healthApp");
        System.out.println("Schedule Hit");
        try {
            ApiFuture<QuerySnapshot> querySnapshot = collectionRef.get();

            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                Patient patient = document.toObject(Patient.class);
                System.out.println(patient);
                // Check if conditions for sending notification are met
                if (conditionsMet(patient)) {
                    sendNotificationToUser(patient);
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            // Handle exception if needed
        }
    }

    // ... other methods ...

    private boolean conditionsMet(Patient patient) {
        // Implement your logic to check conditions
        LocalTime currentTime = LocalTime.now();

        // Define a custom time format pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        // Format the current time using the formatter
        String formattedTime = currentTime.format(formatter);
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Get the start date as a string from your Patient object
        String startDateString = patient.getStartDate(); // Replace this with actual code to get the start date string

        // Define the date format pattern
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Parse the start date string into a LocalDate
        LocalDate startDate = LocalDate.parse(startDateString, dateFormatter);

        // Compare the current date with the start date
        boolean isAfterOrEqual = currentDate.isAfter(startDate) || currentDate.isEqual(startDate);

        // Print the result
        if (isAfterOrEqual) {
            System.out.println("Current date is after or equal to the start date.");
        } else {
            System.out.println("Current date is before the start date.");
        }
        for(int i=0;i<patient.getDailyOccurrence().toArray().length;i++){
            System.out.println("Daily Occurance"+patient.getDailyOccurrence().toArray()[i]);
            if(formattedTime.equals(patient.getDailyOccurrence().toArray()[i]))
                System.out.println("case match");
        }
        System.out.println("Time"+formattedTime);
        return true;
    }

    private void sendNotificationToUser(Patient patient) {
        // Get the user's FCM token from the patient object (if available)
        String fcmToken = patient.getFcmToken();
        System.out.println("FCM Token"+fcmToken);
        System.out.println("In the Send Notification");
        if (fcmToken != null && !fcmToken.isEmpty()) {
            // Construct and send the notification using your NotificationService
            String title = "Notification Title";
            String body = "Notification Body";
            notificationService.sendNotificationToUser(fcmToken, title, body);
        }
    }

}
