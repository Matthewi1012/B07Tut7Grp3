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
import com.example.b07tut7grp3.admin_main;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Admin_Login extends AppCompatActivity {
    EditText admin_name, password;
    Button LoginButton;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        admin_name = findViewById(R.id.username);
        password = findViewById(R.id.password);
        fAuth = FirebaseAuth.getInstance();
        LoginButton = findViewById(R.id.login);

        LoginButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String username = admin_name.getText().toString().trim();
                String pw = password.getText().toString().trim();

                if (TextUtils.isEmpty(username)){
                    admin_name.setError("Username is Required");
                }

                if (TextUtils.isEmpty(pw)){
                    admin_name.setError("Password is Required");
                }
                fAuth.signInWithEmailAndPassword(username,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Admin_Login.this, "Logged in", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), admin_main.class));
                        }else{
                            Toast.makeText(Admin_Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
    });

    }
}