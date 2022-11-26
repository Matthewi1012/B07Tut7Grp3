package com.example.admintools;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.b07tut7grp3.*;
import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.List;

public final class utscCourseModifier extends utscCourse {
    private DatabaseReference dbref;

    public utscCourseModifier(User user) throws ExceptionMessage{
        // initialization in add mode
        //TODO: implement add mode
        //Security check, utscCourseModifier must be instantiated with Admin credentials
        if(!(user instanceof Admin)) throw new ExceptionMessage("Action restricted");
        dbref = FirebaseDatabase.getInstance().getReference().getRoot().child("Courses");

    }
    public utscCourseModifier(User user, Course course) throws ExceptionMessage {
        if (!(user instanceof Admin)) throw new ExceptionMessage("Action restricted");
        dbref = FirebaseDatabase.getInstance().getReference().getRoot().child("Courses");
        HashMap<String, Object> courseMap = new HashMap<>();
        HashMap<String, Object> detailsMap = new HashMap<>();
        detailsMap.put("Prerequisites", course.getPrerequisites().toArray());
        detailsMap.put("Subject", course.getSubject().name());
        String[] semester = new String[course.getSemester().size()];
        int iter = 0;
        for (Semester i : course.getSemester()){
            semester[iter] = i.name();
            iter++;
        }
        detailsMap.put("Semester", semester);
        courseMap.put(course.getCourseId(), detailsMap);
        dbref.setValue(courseMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                MessageSystem message = new MessageSystem("Successfully uploaded course.");
                message.successMessage();
            }
        });
    }
    public void setCourseID(String id){
        // TODO: implement add/change course id

    }
    public void setPrereqs(List<String> prereqs, String id){
        // TODO: implement add/change prereqs
    }
    public void setSemester(List<Semester> semesters, String id){
        // TODO: implement add/change semester
    }
    public void setSubject(Subject sub, String id){
        // TODO: implement add/change subject
    }
    public void deleteData(String id){
        dbref.child(id).setValue(null);
    }
}
