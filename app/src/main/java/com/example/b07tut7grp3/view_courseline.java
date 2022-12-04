package com.example.b07tut7grp3;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admintoolsUTSC.Adapter;
import com.example.admintoolsUTSC.AddCourse;
import com.example.admintoolsUTSC.Course_admin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class view_courseline extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    Adapter_view_courseline Adapter;
    ArrayList<courseline> list;

    private SharedPreferences sharedPreferences;
    private String userID;
    private utscStudent student;

    Subject subject;
    String courseName;
    String Name;
    String Prerequisites;

    int year = 2022;
    Semester semester = Semester.FALL;

    private void displayInfo(List<String> ordered_timeline) {
        FirebaseDatabase.getInstance().getReference("Courses")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        System.out.println(snapshot);
                        list.clear();

                        // create a list of course to take
                        HashMap<String, Course> needToTake = new HashMap<>();
                        for (String code : ordered_timeline) {
                            try {
                                Course course = new utscCourse(snapshot, code);
                                System.out.println("----------");
                                System.out.println(code);
                                System.out.println(course);
                                System.out.println("----------");
                                needToTake.put(code, course);
                            } catch (ExceptionMessage e) {
                                e.printStackTrace();
                            }
                        }


                        List<String> taken = new ArrayList<>(student.coursesTaken);
                        List<String> newTaken = new ArrayList<>();

                        int currYear = year;
                        Semester currSemester = semester;

                        // remove all the taken course
                        List<String> keys = new ArrayList<>(needToTake.keySet());
                        for (String code : keys) {
                            if (taken.contains(code))
                                needToTake.remove(code);
                        }

                        System.out.println(needToTake.size());
                        while (needToTake.size() > 0) {
                            for (Course course : needToTake.values()) {
//                                        System.out.println("ID: " + course.getCourseId());
//
//                                        System.out.println("PRE: ");
//                                        for (String pre: course.getPrerequisites())
//                                            System.out.println(pre);
//
//                                        System.out.println("SEM: ");
//                                        for (Semester sem : course.getSemester())
//                                            System.out.println(sem);


                                boolean hasAllPre = taken.containsAll(course.getPrerequisites());
                                boolean hasOffer = course.getSemester().contains(currSemester);

                                if (hasAllPre && hasOffer) {
                                    newTaken.add(course.getCourseId());
                                }
                            }

                            System.out.println(currYear + " " + currSemester + " " + newTaken.size());

                            // add a new row to TABLE 2
                            list.add(new courseline(currYear, currSemester, newTaken));

                            // remove all the new taken courses from needTOTake
                            for (String takenCode : newTaken)
                                needToTake.remove(takenCode);

                            //
                            taken.addAll(newTaken);
                            newTaken.clear();

                            // update to next session
                            if (currSemester == Semester.FALL) {
                                currSemester = Semester.WINTER;
                                currYear += 1;
                            } else if (currSemester == Semester.WINTER)
                                currSemester = Semester.SUMMER;
                            else if (currSemester == Semester.SUMMER)
                                currSemester = Semester.FALL;

                        }

                        Adapter.notifyDataSetChanged();
//                                System.out.println("===================================");
//                                for (courseline cl : list)
//                                    System.out.println(cl.session + " " + cl.courses);
//                                System.out.println("===================================");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }});
    }
    private void generateTimeline(){
        FirebaseDatabase.getInstance()
                .getReference("Users").child("Students")
                .child("utscStudents").child(userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        student = new utscStudent(snapshot);
                        displayInfo(student.getPlannedCourses());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courseline);

        sharedPreferences = getApplicationContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("user", "");

        recyclerView = findViewById(R.id.course);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list = new ArrayList<>();
        Adapter = new Adapter_view_courseline(this, list);
        recyclerView.setAdapter(Adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(view_courseline.this));
        generateTimeline();
    }
}