package com.example.b07tut7grp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentHomePage extends AppCompatActivity {

    private Button courseListBtn;
    SharedPreferences sharedPreferences;
    Student student;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_page);
        sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);
        userID = sharedPreferences.getString("user","");
        DatabaseReference dbref = FirebaseDatabase.getInstance()
                .getReference().getRoot().child("Users").child("Students")
                .child("utscStudents").child(userID);
        dbref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                student = new utscStudent(task.getResult());
            }
        });
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