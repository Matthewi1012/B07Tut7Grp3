package com.example.b07tut7grp3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    TextView alreadyHaveAccount;
    EditText inputEmail,inputPassword,inputConfirmPassword;
    Button RegisterButton;
    String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    //please make sure to enable email/password authentication on Firebase!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        alreadyHaveAccount=findViewById(R.id.alreadyHaveAccount);

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputConfirmPassword=findViewById(R.id.inputConfirmPassword);
        RegisterButton=findViewById(R.id.RegisterButton);
        progressDialog=new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformAuth();
            }
        });
    }

    private void PerformAuth() {
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String confirmPassword=inputConfirmPassword.getText().toString();

        if(!email.matches(emailPattern))
        {
            inputEmail.setError("Please enter valid email address");
        }else if(password.isEmpty() || password.length()<6){
            inputPassword.setError("Please enter proper password");
        }else if(!password.equals(confirmPassword))
        {
            inputConfirmPassword.setError("Password does not match");
        }else
        {
            progressDialog.setMessage("Registering...Please wait");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(RegisterActivity.this, StudentActivity.class);
        //change "Student_home_activity" to the right name for student home page and keep the ".class"!!
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}