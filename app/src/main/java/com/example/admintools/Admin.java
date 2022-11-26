package com.example.admintools;

import com.example.b07tut7grp3.Course;
import com.example.b07tut7grp3.ExceptionMessage;
import com.example.b07tut7grp3.User;

public final class Admin extends User{
    // A singleton Admin class
    private static Admin admin;

    private utscCourseModifier courseMod;
    private Admin() throws ExceptionMessage {
        // Admin constructor here
        courseMod = new utscCourseModifier(this);
    }
    public static Admin getInstance() throws ExceptionMessage{
        if(admin == null) admin = new Admin();
        return admin;
    }
    public void addCourse(Course course) throws ExceptionMessage{
        // TODO: implement addCourse (use setCourseID, setSemester, etc setter methods)
        courseMod = new utscCourseModifier(this, course);
    }
}
