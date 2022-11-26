package com.example.b07tut7grp3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class utscStudent extends Student{
    protected Subject currentPOSt;
    public utscStudent(String firstName, String lastName, HashSet<String> coursesTaken,
                   int currentYear, String currentSchool, Subject post,
                       String email, String username){
        this.firstName = firstName;
        this.lastName = lastName;
        this.coursesTaken = coursesTaken;
        this.plannedCourses = new HashSet<>();
        this.currentYear = currentYear;
        this.currentSchool = currentSchool;
        currentPOSt = post;
        this.email = email;
        this.username = username;
    }
    public utscStudent(DatabaseReference dbref){
        //Gets an existing student
    }
    private void uploadData(){
        Map<String, Object> userMap = new HashMap<>();
        Map<String, Object> detailsMap = new HashMap<>();
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().getRoot()
                .child("Users").child("Students").child("utscStudents");
        detailsMap.put("Email", this.email);
        detailsMap.put("POst", this.currentPOSt.name());
        detailsMap.put("currentSchool", this.currentSchool);
        detailsMap.put("currentYear", this.currentYear);
        detailsMap.put("firstName", this.firstName);
        detailsMap.put("lastName", this.lastName);
        String[] coursesTaken = new String[this.coursesTaken.size()];
        this.coursesTaken.toArray(coursesTaken);
        String[] plannedCourses = new String[this.plannedCourses.size()];
        this.plannedCourses.toArray(plannedCourses);
        detailsMap.put("coursesTaken", coursesTaken);
        detailsMap.put("plannedCourses", plannedCourses);
        userMap.put(username, detailsMap);
        dbref.setValue(userMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                MessageSystem message = new MessageSystem("Successfully uploaded data");
                message.successMessage();
            }
        });
    }
    @Override
    public double getCreditsEarned() {
        return coursesTaken.size() * 0.5;
    }
    // getter methods
    public Subject getCurrentPOSt() {
        return currentPOSt;
    }
}
