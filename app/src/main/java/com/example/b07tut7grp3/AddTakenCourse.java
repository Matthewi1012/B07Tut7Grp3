package com.example.b07tut7grp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddTakenCourse extends AppCompatActivity {

    utscStudent student;
    SharedPreferences sharedPreferences;
    String userId;
    List<Course> courses;
    ArrayList<AddListModel> list = new ArrayList<>();
    utscCourse course;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_taken_course);
        sharedPreferences = getApplicationContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user","");
//        dbref = FirebaseDatabase.getInstance()
//                .getReference().getRoot().child("Users").child("Students")
//                .child("utscStudents").child(userId);
//        dbref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                try {
//                    student = new utscStudent(task.getResult());
//                } catch (ExceptionMessage e) {
//                    e.getMessage();
//                }
//            }
//        });
        Toolbar toolbar = findViewById(R.id.student_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Taken Course");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        RecyclerView recyclerView = findViewById(R.id.add_recycler_view);
//        setUpAddModels();
//        AddM_RecyclerViewAdap adapter = new AddM_RecyclerViewAdap(this, list);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUpAddModels() {
        for(int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            String prereqs = String.join(", ", course.getPrerequisites());
            list.add(new AddListModel(course.getCourseId(), course.getName(), course.getSubject().toString(), prereqs));
        }
    }

}