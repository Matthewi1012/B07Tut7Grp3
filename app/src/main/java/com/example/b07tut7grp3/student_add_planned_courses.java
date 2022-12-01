package com.example.b07tut7grp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class student_add_planned_courses extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    List<String> courses;
    List<String> completed;
    Spinner eligibleCourseList;
    String uid = "EZOZ4QTpQLXCD8WkOxnhclkE0iO2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add_planned_courses);

        courses = new ArrayList<>();
        completed = new ArrayList<>();
        eligibleCourseList = findViewById(R.id.coursesList);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eligibleCourseList.setAdapter(adapter);
        eligibleCourseList.setOnItemSelectedListener(this);


        DatabaseReference database = FirebaseDatabase.getInstance().getReference().getRoot().child("Courses");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses.clear();
                for (DataSnapshot snap: snapshot.getChildren()) {
                    courses.add(snap.getKey());
                }
                DatabaseReference database2 = FirebaseDatabase.getInstance().getReference().getRoot().child("Users").child("Students").child("utscStudents").child(uid).child("coursesTaken");
                database2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        completed.clear();
                        for (DataSnapshot snap: snapshot.getChildren()) {
                            completed.add(snap.getValue(String.class));
                            //System.out.println(snap.getValue());
                        }
                        //System.out.println(completed.toString());
                        courses.removeAll(completed);
                        //System.out.println(courses.toString());
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}