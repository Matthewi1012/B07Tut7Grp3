package com.example.b07tut7grp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

public class StudentHomePage extends AppCompatActivity {

    private Button courseListBtn;
    //Code for new Student home page
    //will have area to view different course timelines
    // button to go to completed courses list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);
        Toolbar toolbar = findViewById(R.id.student_home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Student Home Page");

        courseListBtn = (Button) findViewById(R.id.go_to_taken_course_list);
        courseListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStudentCoursesTaken();
            }
        });
    }

    public void openStudentCoursesTaken(){
        Intent intent = new Intent(this, StudentCoursesTaken.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout){
            startActivity(new Intent(this, StudentHomePage.class));
        }
        return super.onOptionsItemSelected(item);
    }
}