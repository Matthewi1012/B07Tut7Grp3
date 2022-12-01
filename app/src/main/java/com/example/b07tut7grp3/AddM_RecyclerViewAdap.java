package com.example.b07tut7grp3;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class AddM_RecyclerViewAdap extends RecyclerView.Adapter<AddM_RecyclerViewAdap.MyAddViewHolder> {

    Context context;
    ArrayList<AddListModel> adminCourses;
    SharedPreferences sharedPreferences;
    DatabaseReference dbref;
    Student student;

    public AddM_RecyclerViewAdap(Context context, ArrayList<AddListModel> adminCourses) {
        this.context = context;
        this.adminCourses = adminCourses;
    }

    @NonNull
    @Override
    public AddM_RecyclerViewAdap.MyAddViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_taken_course_list_item, parent, false);
        return new AddM_RecyclerViewAdap.MyAddViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddM_RecyclerViewAdap.MyAddViewHolder holder, int position) {
        int p = position;
        holder.courseId.setText(adminCourses.get(p).getCourseId());
        holder.courseName.setText(adminCourses.get(p).getCourseName());
        holder.subject.setText(adminCourses.get(p).getCourseSubject());
        holder.prereqs.setText(adminCourses.get(p).getPrereqs());
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sharedPreferences = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
//                String userId = sharedPreferences.getString("user","");
//                dbref = FirebaseDatabase.getInstance()
//                        .getReference().getRoot().child("Users").child("Students")
//                        .child("utscStudents").child(userId);
//                dbref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DataSnapshot> task) {
//                        if(student.coursesTaken.contains(adminCourses.get(p))){
//                            Toast.makeText(context,"You already have taken this course", Toast.LENGTH_SHORT).show();
//                        }else{
//                            student.addPlannedCourse(adminCourses.get(p).getCourseId());
//                        }
//                    }
//                });
                Toast.makeText(context, adminCourses.get(p).getCourseId() + " added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return adminCourses.size();
    }

    public static class MyAddViewHolder extends RecyclerView.ViewHolder {

        TextView courseId;
        TextView courseName;
        TextView subject;
        TextView prereqs;
        Button addBtn;

        public MyAddViewHolder(@NonNull View itemView) {
            super(itemView);
            courseId = itemView.findViewById(R.id.course_id);
            courseName = itemView.findViewById(R.id.course_name);
            subject = itemView.findViewById(R.id.subject_txt);
            prereqs = itemView.findViewById(R.id.prereq_txt);
            addBtn = itemView.findViewById(R.id.add_btn);
        }
    }
}
