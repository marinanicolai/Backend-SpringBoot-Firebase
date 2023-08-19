package com.healthApp.springboot;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.cloud.Timestamp;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class Patient {
    private String document_user_id;
    private String name;
    private Long age;
    public String user_id;
    private String dob;
    private String startDate;
    private List<String> dailyOccurrence;
    private Integer dailyDosageCount;
    private String medicineName;

    // ... other methods ...

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setDailyOccurrence(List<String> dailyOccurrence) {
        this.dailyOccurrence = dailyOccurrence;
    }

    public void setDailyDosageCount(Integer dailyDosageCount) {
        this.dailyDosageCount = dailyDosageCount;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getName() {
        return name;
    }

    public Long getAge() {
        return age;
    }

    public String getDob() {
        return dob;
    }

    public String getStartDate() {
        return startDate;
    }

    public List<String> getDailyOccurrence() {
        return dailyOccurrence;
    }

    public Integer getDailyDosageCount() {
        return dailyDosageCount;
    }

    public String getMedicineName() {
        return medicineName;
    }

    // Manually define getter for document_user_id
    public String getDocument_user_id() {
        return document_user_id;
    }

    // Manually define setter for document_user_id
    public void setDocument_user_id(String document_user_id) {
        this.document_user_id = document_user_id;
    }
    @Override
    public String toString() {
        return "Patient{" +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", dob='" + dob + '\'' +
                ", startDate='" + startDate + '\'' +
                ", dailyOccurrence=" + dailyOccurrence +
                ", dailyDosageCount=" + dailyDosageCount +
                ", medicineName='" + medicineName + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
