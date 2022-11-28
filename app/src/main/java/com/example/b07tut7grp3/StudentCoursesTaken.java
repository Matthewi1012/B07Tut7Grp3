package com.example.b07tut7grp3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

public class StudentCoursesTaken extends AppCompatActivity {

    private Button addTakenCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_courses_taken);

        Toolbar toolbar = findViewById(R.id.courses_taken_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Courses Taken");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addTakenCourse = (Button) findViewById(R.id.to_add_taken_courses);
        addTakenCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddTakenCourse();
            }
        });
    }
    public void openAddTakenCourse() {
        Intent intent = new Intent(this, AddTakenCourse.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}