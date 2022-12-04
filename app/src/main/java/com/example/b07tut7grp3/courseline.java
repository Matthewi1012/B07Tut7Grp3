package com.example.b07tut7grp3;

import java.util.ArrayList;
import java.util.List;

public class courseline {

    public String session;
    public String courses;

    public courseline(int year, Semester semester, List<String> courses){
        this.session = year + " " + semester;
        this.courses = String.join(",", courses);
    }

    public String getsession(){
        return session;
    }
    public String getcourses(){
        return courses;
    }
}
