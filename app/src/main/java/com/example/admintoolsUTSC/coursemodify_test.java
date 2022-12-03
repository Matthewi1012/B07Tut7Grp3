package com.example.admintoolsUTSC;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.b07tut7grp3.R;
import com.example.b07tut7grp3.RegisterActivity;

public class coursemodify_test extends AppCompatActivity {
    Button a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coursemodify_test);


        String name = getIntent().getStringExtra("courseName");
        a = findViewById(R.id.button2);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(coursemodify_test.this, name, Toast.LENGTH_SHORT).show();
            }
        });

    }




}
