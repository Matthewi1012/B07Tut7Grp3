package com.example.admintoolsUTSC;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.b07tut7grp3.R;

public class admin_main extends AppCompatActivity {
    Button addCourseBtn;
    Button viewcourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        addCourseBtn=findViewById(R.id.addCourseButton);
        viewcourse=findViewById(R.id.viewCoursesButton);

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddCourse.class));
            }
        });

        viewcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin_view_course.class));
            }
        });

    }
}