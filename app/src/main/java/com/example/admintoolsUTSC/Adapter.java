package com.example.admintoolsUTSC;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07tut7grp3.Course;
import com.example.b07tut7grp3.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    ArrayList<Course_admin> list;

    public  Adapter(Context context, ArrayList<Course_admin> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.course,parent,false);
        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course_admin course = list.get(position);
        holder.courseName.setText(course.getCourseName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView courseName;
        public ViewHolder(View courseView){
            super((courseView));
            courseName = courseView.findViewById((R.id.tvcourseName));
        }

    }
}
