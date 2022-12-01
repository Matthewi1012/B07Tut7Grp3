package com.example.admintoolsUTSC;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.b07tut7grp3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_Login extends AppCompatActivity {
    EditText admin_name, password;
    Button Login;
    FirebaseAuth fAuth;

    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference firebaseDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        admin_name = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        fAuth = FirebaseAuth.getInstance();
        Login = findViewById(R.id.LoginButton);

        Login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String username = admin_name.getText().toString().trim();
                String pw = password.getText().toString().trim();

                if (TextUtils.isEmpty(username)){
                    admin_name.setError("Username is Required");
                    return;
                }

                if (TextUtils.isEmpty(pw)){
                    password.setError("Password is Required");
                    return;
                }
                fAuth.signInWithEmailAndPassword(username,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                            database.child("Users")
                                    .child("Admin")
                                    .child(userID)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            if (dataSnapshot.exists()) {
                                                Toast.makeText(Admin_Login.this, "Logged in", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(Admin_Login.this,
                                                        admin_main.class));
                                            } else {
                                                Toast.makeText(Admin_Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                                FirebaseAuth.getInstance().signOut();
                                                startActivity(new Intent(getApplicationContext(), Admin_Login.class));
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) { }
                                    });
                        }else{
                            Toast.makeText(Admin_Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
    });

    }
}