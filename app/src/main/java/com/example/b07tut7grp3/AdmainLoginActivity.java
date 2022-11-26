package com.example.b07tut7grp3;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdmainLoginActivity extends AppCompatActivity {

        EditText inputEmail,inputPassword;
        Button LoginButton;
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        ProgressDialog progressDialog;

        FirebaseAuth mAuth;
        FirebaseUser mUser;
        //please make sure to enable email/password authentication on Firebase!!

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            inputEmail=findViewById(R.id.inputEmail);
            inputPassword=findViewById(R.id.inputPassword);
            LoginButton=findViewById(R.id.LoginButton);
            progressDialog=new ProgressDialog(this);

            mAuth=FirebaseAuth.getInstance();
            mUser=mAuth.getCurrentUser();



            LoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PerformLogin();
                }
            });

        }

        private void PerformLogin() {
            String email=inputEmail.getText().toString();
            String password=inputPassword.getText().toString();

            if(!email.matches(emailPattern))
            {
                inputEmail.setError("Please enter valid email address");
            }else if(password.isEmpty() || password.length()<6)
            {
                inputPassword.setError("Please enter proper password");
            }else
            {
                progressDialog.setMessage("Registering...Please wait");
                progressDialog.setTitle("Registration");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            sendUserToNextActivity();
                            Toast.makeText(AdmainLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AdmainLoginActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

        private void sendUserToNextActivity() {
            Intent intent=new Intent(AdmainLoginActivity.this, admin_main.class);
            //change "Student_home_activity" to the right name for student home page and keep the ".class"!!
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


}

