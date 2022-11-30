package com.example.b07tut7grp3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.admintoolsUTSC.Admin;
import com.example.admintoolsUTSC.AdminLoginHelper;
import com.example.admintoolsUTSC.Admin_Login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AddCourse extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String course_id;
    List<String> prerequisites;
    List<Semester> semester;
    List<String> subjects;
    String course_name;
    String course = "[A-Z]{4}[0-9]{2}";

    EditText courseCode, prereqs, courseName;
    Switch fallSW, winterSW,summerSW;
    Button AddPrereqBtn;
    FloatingActionButton addCourseBtn;
    Spinner spinner;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        courseCode = findViewById(R.id.courseCodeField);
        courseName = findViewById(R.id.courseNameField);
        prereqs = findViewById(R.id.prereqField);
        addCourseBtn = findViewById(R.id.floatingActionButton3);
        AddPrereqBtn = findViewById(R.id.addPrereq);
        fallSW = findViewById(R.id.fall);
        winterSW = findViewById(R.id.winter);
        summerSW = findViewById(R.id.summer);
        spinner = findViewById(R.id.spinner);

        subjects = new ArrayList<String>();

        prerequisites = new ArrayList<>();
        semester = new ArrayList<>();

        for (Subject s : Subject.values()) {
            System.out.println(s.name().getClass().getName());
            subjects.add(s.name());
        }
        String[] arr = subjects.toArray(new String[subjects.size()]);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        AddPrereqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prerequisite;
                prerequisite = prereqs.getText().toString();
                prerequisites.add(prerequisite);
                prereqs.getText().clear();
                Toast.makeText(AddCourse.this, "Added Prerequisite", Toast.LENGTH_SHORT).show();
            }
        });

       fallSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if (b){
                    semester.add(Semester.FALL);
               }
               else{
                   semester.remove(Semester.FALL);
               }
           }
       });

        winterSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    semester.add(Semester.WINTER);
                }
                else{
                    semester.remove(Semester.WINTER);
                }
            }
        });

        summerSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    semester.add(Semester.SUMMER);
                }
                else{
                    semester.remove(Semester.SUMMER);
                }
            }
        });

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                course_id = courseCode.getText().toString();
                course_name = courseName.getText().toString();
                String subject = spinner.getSelectedItem().toString();
                HashMap<String, Object> courseMap = new HashMap<>();
                courseMap.put("Prerequisites", prerequisites);
                courseMap.put("Subject", subject);
                courseMap.put("Name", course_name);
                String[] semesters = new String[semester.size()];
                int iter = 0;
                for (Semester i : semester){
                    semesters[iter] = i.name();
                    iter++;
                }

                dbref = FirebaseDatabase.getInstance().getReference().getRoot().child("Courses");
                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().getRoot().child("Courses");
                dbref.child(course_id).setValue(courseMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        prerequisites.clear();
                        semester.clear();
                        Toast.makeText(AddCourse.this, "Added Course", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), admin_main.class));
                    }


            });

    }
});
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

        spinner.setSelection(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

