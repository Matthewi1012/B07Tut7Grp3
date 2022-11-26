package com.example.b07tut7grp3;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashSet;
import java.util.List;

abstract class Student extends User{
    public abstract double getCreditsEarned();
    protected String firstName, lastName;
    protected List<String> coursesTaken;
    protected List<String> plannedCourses;
    protected int currentYear;
    protected String currentSchool;

    public void addPlannedCourse(String planned){
        plannedCourses.add(planned);
    }
    // Course completion module
    public void completeCourse(String completed){
        if(!plannedCourses.contains(completed)){
            MessageSystem notice = new MessageSystem("Notice: course not found in planning, " +
                    "adding course anyways");
            addPlannedCourse(completed);
            completeCourse(completed);
        }
        coursesTaken.add(completed);
        plannedCourses.remove(completed);
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference()
                .getRoot().child("Users").child("Students");
        updateCourses(dbref);

    }
    abstract void updateCourses(DatabaseReference dbref);
    // Getter methods
    public int getCurrentYear(){
        return currentYear;
    }
    public List<String> getCoursesTaken(){ return coursesTaken; }
    public List<String> getPlannedCourses(){ return plannedCourses; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCurrentSchool(){
        return currentSchool;
    }

}
