package com.example.b07tut7grp3;

public class courseline {


    public courseline(String Subject, String courseName, String Name, String Prerequisites){
        this.Subject = Subject;
        this.courseName = courseName;
        this.Prerequisites = Prerequisites;
        this.Name = Name;
    }


    String courseName;
    public String getCourseName(){
        return courseName;
    }

    String Subject;
    public String getSubject(){
        return Subject;
    }

    String Name;
    public String getName(){
        return Name;
    }

    String Prerequisites;
    public String getPrerequisites(){
        return Prerequisites;
    }
}
