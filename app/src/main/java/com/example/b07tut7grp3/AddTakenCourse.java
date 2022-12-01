package com.example.b07tut7grp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddTakenCourse extends AppCompatActivity {

    Student student;
    SharedPreferences sharedPreferences;
    String userId;
    List<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_taken_course);
        sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);
        userId = sharedPreferences.getString("user","");
        DatabaseReference dbref = FirebaseDatabase.getInstance()
                .getReference().getRoot().child("Users").child("Students")
                .child("utscStudents").child(userId);
        dbref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                student = new utscStudent(task.getResult());

            }
        });
        courses = student.getCourseList();
        Toolbar toolbar = findViewById(R.id.student_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Taken Course");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}