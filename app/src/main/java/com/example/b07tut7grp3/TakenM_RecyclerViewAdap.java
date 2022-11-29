package com.example.b07tut7grp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TakenM_RecyclerViewAdap extends RecyclerView.Adapter<TakenM_RecyclerViewAdap.MyTakenViewHolder> {

    Context context;
    ArrayList<TakenListModel> coursesTaken;

    public TakenM_RecyclerViewAdap(Context context, ArrayList<TakenListModel> coursesTaken) {
        this.context = context;
        this.coursesTaken = coursesTaken;
    }

    @NonNull
    @Override
    public TakenM_RecyclerViewAdap.MyTakenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.taken_course_list_item, parent, false);
        return new TakenM_RecyclerViewAdap.MyTakenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TakenM_RecyclerViewAdap.MyTakenViewHolder holder, int position) {
        int p = position;
        holder.courseId.setText(coursesTaken.get(p).getCourseId());
        holder.courseName.setText(coursesTaken.get(p).getCourseName());
        holder.subject.setText(coursesTaken.get(p).getCourseSubject());
    }

    @Override
    public int getItemCount() {
        return coursesTaken.size();
    }

    public static class MyTakenViewHolder extends RecyclerView.ViewHolder {
        TextView courseId;
        TextView courseName;
        TextView subject;

        public MyTakenViewHolder(@NonNull View itemView) {
            super(itemView);
            courseId = itemView.findViewById(R.id.taken_course_code);
            courseName = itemView.findViewById(R.id.taken_course_name);
            subject = itemView.findViewById(R.id.taken_subject);
        }
    }
}
