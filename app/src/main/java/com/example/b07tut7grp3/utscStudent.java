package com.example.b07tut7grp3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class utscStudent extends Student{
    protected Subject currentPOSt;
    public utscStudent(String firstName, String lastName, List<String> coursesTaken,
                   int currentYear, String currentSchool, Subject post,
                       String email, String username){
        this.firstName = firstName;
        this.lastName = lastName;
        this.coursesTaken = coursesTaken;
        this.plannedCourses = new ArrayList<>();
        this.currentYear = currentYear;
        this.currentSchool = currentSchool;
        currentPOSt = post;
        this.email = email;
        this.username = username;
    }
    public utscStudent(DataSnapshot dbref) throws ExceptionMessage {
        //Gets an existing student
        /*
        To whoever uses this function
        Add the following code:
        Database dbref = FirebaseDatabase.getInstance()
                .getReference().getRoot().child("Users").child("Students")
                .child("utscStudents").child(whatever the username is);
        dbref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void OnComplete(@NonNull Task<DataSnapshot> task){
                utscStudent student = new utscStudent(task.getResult());
                // can add a success message or something here...
            }
        });
        // continue code here...
         */
        try {
            this.firstName = dbref.child("FirstName").getValue().toString();
            this.lastName = dbref.child("LastName").getValue().toString();
            this.username = dbref.getKey();
            this.email = dbref.child("Email").getValue().toString();
            this.currentSchool = "UTSC";
            this.currentYear = Integer.parseInt(dbref.child("currentYear").getValue().toString());
            this.currentPOSt = Subject.valueOf(dbref.child("POst").getValue().toString());
            List<String> coursesTaken = new ArrayList<>();
            List<String> plannedCourses = new ArrayList<>();
            for(DataSnapshot i : dbref.child("coursesTaken").getChildren()){
                coursesTaken.add((String)(i.getValue()));
            }
            for(DataSnapshot i : dbref.child("plannedCourses").getChildren()){
                plannedCourses.add((String)(i.getValue()));
            }
            this.coursesTaken = coursesTaken;
            this.plannedCourses = plannedCourses;
        }
        catch(NullPointerException e){
            throw new ExceptionMessage("Could not find data!" + e.getMessage());
        }
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
    protected void updateCourses(DatabaseReference dbref){
        dbref = dbref.child("utscStudents").child(username);
        Map<String, Object> detailsMap = new HashMap<>();
        String[] coursesTaken = new String[this.coursesTaken.size()];
        this.coursesTaken.toArray(coursesTaken);
        String[] plannedCourses = new String[this.plannedCourses.size()];
        this.plannedCourses.toArray(plannedCourses);
        detailsMap.put("plannedCourses", plannedCourses);
        detailsMap.put("coursesTaken", coursesTaken);
        dbref.updateChildren(detailsMap);
        MessageSystem success = new MessageSystem("added course successfully!");
        success.successMessage();
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
