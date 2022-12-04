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



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courseline);

        sharedPreferences = getApplicationContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("user","");




        recyclerView = findViewById(R.id.course);


        database = FirebaseDatabase.getInstance()
                .getReference().getRoot().child("Users").child("Students")
                 .child("utscStudents").child(userID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list = new ArrayList<>();
        Adapter = new Adapter_view_courseline(this, list);
        recyclerView.setAdapter(Adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(view_courseline.this));



        database.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                student = new utscStudent(snapshot.child("Users").child("Students").child("utscStudents").child(userID));

                utscTimeline timeline = new utscTimeline(student.plannedCourses);
                List<String> ordered_timeline = timeline.topological_sort();

                for(int i = 0; i < ordered_timeline.size(); i++){
                    if(snapshot.child("Courses").hasChild(ordered_timeline.get(i))){
                        subject = Subject.valueOf(snapshot.child("Courses").child(student.coursesTaken.get(i)).child("Subject").getValue().toString());
                        Name = snapshot.child("Courses").child(student.coursesTaken.get(i)).child("Name").getValue().toString();
                        courseName = snapshot.child("Courses").child(student.coursesTaken.get(i)).child("courseName").getValue().toString();
                        Prerequisites = snapshot.child("Courses").child(student.coursesTaken.get(i)).child("Prerequisites").getValue().toString();

                        list.add(new courseline(subject.toString(),courseName, Name, Prerequisites));
                    }

                }
                Adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }
















}
