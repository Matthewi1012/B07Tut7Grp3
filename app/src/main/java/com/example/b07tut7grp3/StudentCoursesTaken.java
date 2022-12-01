package com.example.b07tut7grp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class StudentCoursesTaken extends AppCompatActivity {

    Student student;
    SharedPreferences sharedPreferences;
    String userId;
    ArrayList<TakenListModel> list = new ArrayList<>();
    utscCourse course;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_courses_taken);
        sharedPreferences = getSharedPreferences("sharedPref",MODE_PRIVATE);
        userId = sharedPreferences.getString("user","");
        dbref = FirebaseDatabase.getInstance()
                .getReference().getRoot().child("Users").child("Students")
                .child("utscStudents").child(userId);
        dbref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                student = new utscStudent(task.getResult());
            }
        });
        Toolbar toolbar = findViewById(R.id.courses_taken_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Courses Taken");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.taken_recycler);
        setUpTakenModels();
        //TakenM_RecyclerViewAdap adapter = new TakenM_RecyclerViewAdap(this, list);
        //recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setUpTakenModels(){
        for(int i = 0; i < student.coursesTaken.size(); i++){
            dbref = FirebaseDatabase.getInstance()
                    .getReference().getRoot().child("Courses").child(student.coursesTaken.get(i));
            int finalI = i;
            dbref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    try {
                        course = new utscCourse(task.getResult(), student.coursesTaken.get(finalI));
                    } catch (ExceptionMessage e) {
                    }
                }
            });
            list.add(new TakenListModel(course.getCourseId(), course.getName(), course.getSubject().toString()));
        }
    }
}

