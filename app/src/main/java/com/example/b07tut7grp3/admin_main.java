package com.example.b07tut7grp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.admintoolsUTSC.AdminLoginHelper;

public class admin_main extends AppCompatActivity {
    Button addCourseBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        addCourseBtn=findViewById(R.id.addCourseButton);

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddCourse.class));
            }
        });
    }
}