package com.example.b07tut7grp3;

import java.util.HashSet;

abstract class Student extends User{
    public abstract double getCreditsEarned();
    protected String firstName, lastName;
    protected HashSet<String> coursesTaken;
    protected HashSet<String> plannedCourses;
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
        MessageSystem success = new MessageSystem("added course " + completed);
        success.successMessage();
    }
    // Getter methods
    public int getCurrentYear(){
        return currentYear;
    }
    public HashSet<String> getCoursesTaken(){ return coursesTaken; }
    public HashSet<String> getPlannedCourses(){ return plannedCourses; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getCurrentSchool(){
        return currentSchool;
    }

}
