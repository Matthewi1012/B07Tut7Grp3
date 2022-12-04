package com.example.b07tut7grp3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class view_courseline extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    Adapter_view_courseline Adapter;
    ArrayList<courseline> list;

    private SharedPreferences sharedPreferences;
    private String userID;
    private utscStudent student;

    Subject subject;
    String courseName;
    String Name;
    String Prerequisites;

    int year = 2022;
    Semester semester = Semester.FALL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courseline);

        sharedPreferences = getApplicationContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("user","");

        recyclerView = findViewById(R.id.course);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));





        list = new ArrayList<>();
        Adapter = new Adapter_view_courseline(this, list);
        recyclerView.setAdapter(Adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(view_courseline.this));


        FirebaseDatabase.getInstance()
                .getReference("Users").child("Students")
                .child("utscStudents").child(userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // student = snapshot.getValue(utscStudent.class);
                student = new utscStudent(snapshot);



                utscTimeline timeline = new utscTimeline(student.plannedCourses);
                System.out.println(student.plannedCourses);

//                 List<String> ordered_timeline = timeline.topological_sort();

                List<String> ordered_timeline = new ArrayList<>();
                ordered_timeline.add("CSCB07");
                ordered_timeline.add("CSCA48");
                ordered_timeline.add("CSCA08");

                FirebaseDatabase.getInstance().getReference("Courses")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                System.out.println(snapshot);
                                list.clear();

                                // create a list of course to take
                                HashMap<String, Course> needToTake = new HashMap<>();
                                for (String code : ordered_timeline) {
                                    try {
                                        Course course = new utscCourse(snapshot, code);
                                        System.out.println("----------");
                                        System.out.println(code);
                                        System.out.println(course);
                                        System.out.println("----------");
                                        needToTake.put(code, course);
                                    } catch (ExceptionMessage e) {
                                        e.printStackTrace();
                                    }
                                }


                                List<String> taken = new ArrayList<>(student.coursesTaken);
                                List<String> newTaken = new ArrayList<>();

                                int currYear = year;
                                Semester currSemester = semester;

                                // remove all the taken course
                                List<String> keys = new ArrayList<>(needToTake.keySet());
                                for (String code: keys) {
                                    if (taken.contains(code))
                                        needToTake.remove(code);
                                }

                                System.out.println(needToTake.size());
                                while (needToTake.size() > 0) {

                                    for (Course course : needToTake.values()) {
//                                        System.out.println("ID: " + course.getCourseId());
//
//                                        System.out.println("PRE: ");
//                                        for (String pre: course.getPrerequisites())
//                                            System.out.println(pre);
//
//                                        System.out.println("SEM: ");
//                                        for (Semester sem : course.getSemester())
//                                            System.out.println(sem);


                                        boolean hasAllPre = taken.containsAll(course.getPrerequisites());
                                        boolean hasOffer = course.getSemester().contains(currSemester);

                                        if (hasAllPre && hasOffer) {
                                            newTaken.add(course.getCourseId());
                                        }
                                    }

                                    System.out.println(currYear + " " + currSemester + " " + newTaken.size());

                                    // add a new row to TABLE 2
                                    list.add(new courseline(currYear, currSemester, newTaken));

                                    // remove all the new taken courses from needTOTake
                                    for (String takenCode : newTaken)
                                        needToTake.remove(takenCode);

                                    //
                                    taken.addAll(newTaken);
                                    newTaken.clear();

                                    // update to next session
                                    if (currSemester == Semester.FALL) {
                                        currSemester = Semester.WINTER;
                                        currYear += 1;
                                    }
                                    else if (currSemester == Semester.WINTER)
                                        currSemester = Semester.SUMMER;
                                    else if (currSemester == Semester.SUMMER)
                                        currSemester = Semester.FALL;

                                }

                                 Adapter.notifyDataSetChanged();
//                                System.out.println("===================================");
//                                for (courseline cl : list)
//                                    System.out.println(cl.session + " " + cl.courses);
//                                System.out.println("===================================");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {}
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}

        });


    }





























//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_courseline);
//        sharedPreferences = getApplicationContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
//        userID = sharedPreferences.getString("user","");
//
//
//        getSupportActionBar().setTitle("Courses Taken");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        DatabaseReference dbref = FirebaseDatabase.getInstance()
//                .getReference().getRoot();
//        dbref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                list.clear();
//                recyclerView = findViewById(R.id.course);
//                student = new utscStudent(snapshot.child("Users").child("Students").child("utscStudents").child(userID));
//
//
//                for(int i = 0; i < student.coursesTaken.size(); i++){
//                    if(snapshot.child("Courses").hasChild(student.coursesTaken.get(i))){
//                        subject = Subject.valueOf(snapshot.child("Courses").child(student.coursesTaken.get(i)).child("Subject").getValue().toString());
//                        courseName = snapshot.child("Courses").child(student.coursesTaken.get(i)).child("Name").getValue().toString();
//
//                        list.add(new TakenListModel(student.coursesTaken.get(i),courseName, subject.toString()));
//                    }
//                }
//                Adapter = new TakenM_RecyclerViewAdap(StudentCoursesTaken.this, list);
//                recyclerView.setAdapter(Adapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(StudentCoursesTaken.this));
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {}
//        });
//    }
//
//
//
//
//
//
//
//




}
