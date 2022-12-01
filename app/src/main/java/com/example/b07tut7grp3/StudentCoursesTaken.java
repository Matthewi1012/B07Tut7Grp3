package com.example.b07tut7grp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentCoursesTaken extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    String userId;
    ArrayList<TakenListModel> list = new ArrayList<>();
    utscCourse course;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_courses_taken);
        sharedPreferences = getApplicationContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user","");
//        dbref = FirebaseDatabase.getInstance()
//                .getReference().getRoot().child("Users").child("Students")
//                .child("utscStudents").child(userId);
//        ValueEventListener listener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                utscStudent student = new utscStudent(snapshot);
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {}
//        };

        //This sets the toolbar and creates a back arrow button to the parent class (Student Home Page)
        Toolbar toolbar = findViewById(R.id.courses_taken_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Courses Taken");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //This creates the recycler view displaying courses taken and populates it with
//        RecyclerView recyclerView = findViewById(R.id.taken_recycler);
//        setUpTakenModels();
//        TakenM_RecyclerViewAdap adapter = new TakenM_RecyclerViewAdap(this, list);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }else if(item.getItemId() == R.id.add_taken_act){
            Intent intent = new Intent(StudentCoursesTaken.this, AddTakenCourse.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.taken_courses_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void setUpTakenModels(){
//        for(int i = 0; i < student.getCoursesTaken().size(); i++){
//            dbref = FirebaseDatabase.getInstance()
//                    .getReference().getRoot().child("Courses").child(student.getCoursesTaken().get(i).toString());
//            int finalI = i;
//            dbref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DataSnapshot> task) {
//                    try {
//                        course = new utscCourse(task.getResult(), student.coursesTaken.get(finalI));
//                    } catch (ExceptionMessage e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            list.add(new TakenListModel(course.getCourseId(), course.getName(), course.getSubject().toString()));
//        }
    }
}

