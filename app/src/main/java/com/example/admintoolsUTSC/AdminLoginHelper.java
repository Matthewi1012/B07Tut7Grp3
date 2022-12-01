package com.example.admintoolsUTSC;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.b07tut7grp3.admin_main;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginHelper extends AppCompatActivity {

    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference firebaseDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Activity Name", getLocalClassName());


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabaseReference = firebaseDatabase.getReference();

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("Users")
                .child("Admin")
                .child(userID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            Toast.makeText(AdminLoginHelper.this, "Logged in", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AdminLoginHelper.this,
                                    admin_main.class));
                        } else {
                            Toast.makeText(AdminLoginHelper.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getApplicationContext(), Admin_Login.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });
    }
}