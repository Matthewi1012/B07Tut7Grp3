package com.example.b07tut7grp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admintoolsUTSC.Course_admin;

import java.util.ArrayList;

public class Adapter_view_courseline extends RecyclerView.Adapter<Adapter_view_courseline.courseline_ViewHolder> {

    Context context;
    ArrayList<courseline> list;

    public  Adapter_view_courseline(Context context, ArrayList<courseline> list){
        this.context = context;
        this.list = list;

    }



    @NonNull
    @Override
    public Adapter_view_courseline.courseline_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.courseline_item,parent,false);
        return new courseline_ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_view_courseline.courseline_ViewHolder holder, int position) {
        courseline courseline = list.get(position);
        holder.courseName.setText(courseline.getCourseName());
        holder.Subject.setText(courseline.getSubject());
        holder.Name.setText(courseline.getName());
        holder.Prerequisites.setText(courseline.getPrerequisites());

    }







    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class courseline_ViewHolder extends RecyclerView.ViewHolder{

        TextView courseName, Subject, Name, Prerequisites;

        public courseline_ViewHolder(@NonNull View itemView) {
            super(itemView);

            courseName = itemView.findViewById(R.id.course_id);
            Subject = itemView.findViewById(R.id.subject_txt);
            Name = itemView.findViewById(R.id.course_name);
            Prerequisites = itemView.findViewById(R.id.prereq_txt);

        }
    }

}
