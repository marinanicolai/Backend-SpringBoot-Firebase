package com.healthApp.springboot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class HealthAppController {
    public HealthAppService healthAppService;
    public HealthAppController(HealthAppService healthAppService){
        this.healthAppService = healthAppService;
    }
    @PostMapping("/createPatient")
    public ResponseEntity<String> createPatient(@RequestBody Patient patient) throws InterruptedException, ExecutionException{
        return healthAppService.createPatient(patient);
    }
    @GetMapping("/getPatientById")
    public ResponseEntity<Object> readPatient(@RequestParam String documentId) throws InterruptedException,ExecutionException{
        return healthAppService.readPatient(documentId);
    }
    @DeleteMapping("/deletePatient")
    public ResponseEntity<String> deletePatient(@RequestParam String documentId) throws InterruptedException,ExecutionException{
        return healthAppService.deletePatient(documentId);
    }
    @GetMapping("/getAllPatients")
//    @CrossOrigin(origins = "*")
    public ResponseEntity<List<Patient>> getAllPatients(){
        System.out.println("In All Patients");
        return healthAppService.getAllPatients();
    }
    @GetMapping("/")
    public String home() {
        return "index"; // Assuming "index" is the name of your view template
    }
    @PutMapping("/updateFcmToken")
    public ResponseEntity<String> updateFcmToken( @RequestParam String userId,
                                                  @RequestBody User user){
        return healthAppService.updateFcmToken(userId,user);
    }
    @PostMapping("/storeFCMToken")
    public ResponseEntity<String> storeFCMToken(@RequestBody User user)  throws InterruptedException, ExecutionException{
        return healthAppService.storeFCMToken(user);
    }
    @GetMapping("/getAllFcmTokens")
    public ResponseEntity<List<User>> getAllFcmTokens(){
        return healthAppService.getAllFcmTokens();
    }
    @GetMapping("/getFCMToken")
    public ResponseEntity<Object> readFCMToken(@RequestParam String userId) throws InterruptedException,ExecutionException{
        return healthAppService.readFCMToken(userId);
    }

}
