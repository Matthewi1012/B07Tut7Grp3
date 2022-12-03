package com.example.admintoolsUTSC;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.b07tut7grp3.R;
import com.example.b07tut7grp3.Semester;
import com.example.b07tut7grp3.Subject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddCourse extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String course_id;
    List<String> prerequisites;
    List<String> semester;
    List<String> subjects;
    String course_name;
    String course = "[a-zA-Z]{4}[0-9]{2}";
    Boolean checkSwitch1 = false;
    Boolean checkSwitch2= false;
    Boolean checkSwitch3 = false;


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
                prerequisite = prereqs.getText().toString().toUpperCase();
                if  ((TextUtils.isEmpty(prerequisite)) || (!prerequisite.matches(course))){
                    prereqs.setError("Course must contain 4 letters followed by 2 numbers");
                    Toast.makeText(AddCourse.this, "Invalid Course", Toast.LENGTH_SHORT).show();
                    return;
                }
                prerequisites.add(prerequisite);
                prereqs.getText().clear();
                Toast.makeText(AddCourse.this, "Added Prerequisite", Toast.LENGTH_SHORT).show();
            }
        });

       fallSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if (b){
                    semester.add(Semester.FALL.name());
                    checkSwitch1 = true;

               }
               else{
                   semester.remove(Semester.FALL.name());
                   checkSwitch1 = false;
                   System.out.println(semester);

               }
           }
       });

        winterSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    semester.add(Semester.WINTER.name());
                    checkSwitch2 = true;

                }
                else{
                    semester.remove(Semester.WINTER.name());
                    checkSwitch2 = false;

                }
            }
        });

        summerSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    semester.add(Semester.SUMMER.name());
                    checkSwitch3 = true;

                }
                else{
                    semester.remove(Semester.SUMMER.name());
                    checkSwitch3 = false;

                }
            }
        });

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                course_id = courseCode.getText().toString().toUpperCase();
                course_name = courseName.getText().toString();
                String subject = spinner.getSelectedItem().toString();

                if (!course_id.matches(course)) {
                    courseCode.setError("Course must contain 4 letters followed by 2 numbers");
                    Toast.makeText(AddCourse.this, "Invalid Course Code", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(course_name)){
                    courseName.setError("Name cannot be empty");
                    Toast.makeText(AddCourse.this, "Invalid Course Name", Toast.LENGTH_SHORT).show();
                    return;
                }if (prerequisites.isEmpty()){
                    prerequisites.add("0");

                }if (!checkSwitch1 && !checkSwitch2 && !checkSwitch3){
                    Toast.makeText(AddCourse.this, "Select At Least One Semester", Toast.LENGTH_SHORT).show();
                    return;
                }


                HashMap<String, Object> courseMap = new HashMap<>();
                courseMap.put("Prerequisites", prerequisites);
                courseMap.put("Subject", subject);
                courseMap.put("Name", course_name);
                courseMap.put("Semester", semester);

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
        spinner.setSelection(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
