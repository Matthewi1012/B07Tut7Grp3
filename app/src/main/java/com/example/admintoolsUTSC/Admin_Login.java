package com.example.admintoolsUTSC;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class Admin_Login extends AppCompatActivity implements View.OnClickListener{
    EditText admin_name, password;
    Button Login;
    FirebaseAuth fAuth;
    private ProgressBar progressBar;
    private admin_presenter presenter;

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
        Login.setOnClickListener(this);
        presenter = new admin_presenter(new admin_model(), this);
    }


            @Override
            public void onClick(View view) {
                switch (view.getId()) {

                    case R.id.LoginButton:
                        adminLogin();
                        break;

                }
            }


            private void adminLogin() {


                String username = admin_name.getText().toString().trim();
                String pw = password.getText().toString().trim();
                if(username.isEmpty()) {
                    admin_name.setError("Email is required");
                    admin_name.requestFocus();
                    return;
                }else if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                    admin_name.setError("Please enter a valid email");
                    return;
                }

                if(pw.isEmpty()) {
                    password.setError("Password is required");
                    password.requestFocus();
                    return;
                }




                presenter.login(username, pw);


            }
//            @Override
//            public void onClick(View view) {
//                String username = admin_name.getText().toString().trim();
//                String pw = password.getText().toString().trim();
//
//                if (TextUtils.isEmpty(username)){
//                    admin_name.setError("Username is Required");
//                    return;
//                }
//
//                if (TextUtils.isEmpty(pw)){
//                    password.setError("Password is Required");
//                    return;
//                }
//
//                progressBar.setVisibility(View.VISIBLE);
//
//                presenter.login(admin_name, password);
//
////                fAuth.signInWithEmailAndPassword(username,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
////                    @Override
////                    public void onComplete(@NonNull Task<AuthResult> task) {
////                        if(task.isSuccessful()){
////                            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
////                            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
////                            System.out.println(userID);
////                            database.child("Users")
////                                    .child("Admin")
//                                    .child(userID)
//                                    .addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//                                            if (dataSnapshot.exists()) {
//                                                System.out.println(dataSnapshot);
//                                                System.out.println(userID);
//                                                Toast.makeText(Admin_Login.this, "Logged in", Toast.LENGTH_SHORT).show();
//                                                presenter.login(userID);
//
//                                            } else {
//                                                displayError();
//
//                                                FirebaseAuth.getInstance().signOut();
//                                                startActivity(new Intent(getApplicationContext(), Admin_Login.class));
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) { }
//                                    });
//                        }else{
//                            displayError();
//                        }
//                    }
//                });

//            }
//    });


            public void goToStudentPage(String userID) {

                Intent intent = new Intent(Admin_Login.this, Admin_view_course.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }



            public void displayError() {

                Toast.makeText(Admin_Login.this, "Sorry, unable to login! Please try again", Toast.LENGTH_LONG).show();
            }

    }


