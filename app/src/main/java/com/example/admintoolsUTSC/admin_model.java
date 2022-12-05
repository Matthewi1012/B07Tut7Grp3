package com.example.admintoolsUTSC;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



    public class admin_model {

        private FirebaseAuth mAuth;
        private DatabaseReference userRef;
        private DatabaseReference courseRef;


        public admin_model() {
            mAuth = FirebaseAuth.getInstance();
            userRef = FirebaseDatabase.getInstance().getReference("Students");
            courseRef = FirebaseDatabase.getInstance().getReference("Courses");
        }

        public void login(String email, String password, Consumer<String> callback) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        callback.accept(mAuth.getUid());
                    } else {
                        callback.accept(null);
                    }
                }
            });
        }




    }


