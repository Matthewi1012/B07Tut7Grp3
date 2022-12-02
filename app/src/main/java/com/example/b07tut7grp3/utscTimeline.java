package com.example.b07tut7grp3;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.*;

public class utscTimeline {
    private int v; // # of vertices or planned courses
    private List<Integer> adj_list[]; // An adjacency list modelling dependencies
    Map<String, Integer> map; // Mapping planned courses to int

    /**
     * Constructs an adjacency list based on the planned courses
     * Assume that the user has either taken all the prerequisites or has them planned
     * @param plannedCourses A list of planned courses
     */
    public utscTimeline(List<String> plannedCourses){
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().getRoot()
                .child("Courses").child("utscCourses");
        v = plannedCourses.size();
        adj_list = new List[v];
        map = new HashMap<>();
        for(int i = 0; i<plannedCourses.size(); i++)
            map.put(plannedCourses.get(i), i);
        for(int i = 0; i<plannedCourses.size(); i++){
            int finalI = i;
            dbref.child(plannedCourses.get(i)).child("Prerequisites")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot j : snapshot.getChildren()){
                        String str = j.getValue().toString();
                        if(plannedCourses.contains(str)){
                            adj_list[map.get(str)].add(map.get(plannedCourses.get(finalI)));
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }});
        }
    }

    /**
     * Uses topological sort to schedule courses by prerequisites
     * This implementation does not have cycle detection, so make sure to construct
     * utscTimeline without cycles in plannedCourses!
     * @return A list of courses in topological order
     */
    public List<String> topological_sort(){
        Vector<Integer> completed_order = new Vector<>();
        int[] in_deg = new int[v]; // Array containing number of in degrees
        Arrays.fill(in_deg, 0);
        for(int i = 0; i<v; i++){
            List<Integer> indeg_counter = adj_list[i]; // For counting in degrees
            for(int j : indeg_counter){
                in_deg[j]++;
            }
        }
        Queue<Integer> order = new LinkedList<>(); // Initialize queue
        for(int i = 0; i<v; i++)
            if(in_deg[i] == 0) order.add(i); // Add vertices with in-degree of 0
        while(!order.isEmpty()){
            int front = order.poll();
            completed_order.add(front);
            for(Integer vertex : adj_list[front]){
                in_deg[vertex]--; // Remove in-degree associated with the first element
                if(in_deg[vertex] == 0) order.add(vertex);
            }
        }
        return parse_list(completed_order);
    }
    private List<String> parse_list(Vector<Integer> order){
        Map<Integer, String> invertMap = new HashMap<>();
        for(Map.Entry<String, Integer> entry : map.entrySet())
            invertMap.put(entry.getValue(), entry.getKey());
        List<String> ordered_plan = new ArrayList<>();
        for(int i : order)
            ordered_plan.add(invertMap.get(i));
        return ordered_plan;
    }
}
