package com.rohit.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    EditText usernameSignup,emailSignup,passwordSignup;
    TextView btnSignup,btnUpload;
    ProgressBar pgBarSignup;
    private FirebaseAuth mAuth;
    public static boolean imgUpload  = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        usernameSignup = findViewById(R.id.usernameSignup);
        emailSignup = findViewById(R.id.emailSignup);
        passwordSignup = findViewById(R.id.passwordSignup);
        btnSignup = findViewById(R.id.btnSignup);
        pgBarSignup = findViewById(R.id.pgBarSignup);


        pgBarSignup.setVisibility(View.INVISIBLE);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usernameSignup.getText().toString().equals("")){
                    usernameSignup.setError("Enter Usrname");
                }else if(emailSignup.getText().toString().equals("")){
                    emailSignup.setError("Enter Email");
                }else if(passwordSignup.getText().toString().equals("")){
                    passwordSignup.setError("Enter Password");
                }else{
                    pgBarSignup.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(emailSignup.getText().toString(),passwordSignup.getText().toString())
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignupActivity.this, "Siggnedup Successfully", Toast.LENGTH_SHORT).show();

                                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users")
                                                .child(FirebaseAuth.getInstance().getUid());

                                        userRef.child("Email").setValue(emailSignup.getText().toString());
                                        userRef.child("Password").setValue(passwordSignup.getText().toString());
                                        userRef.child("Username").setValue(usernameSignup.getText().toString());
                                        userRef.child("uid").setValue(FirebaseAuth.getInstance().getUid());

                                        startActivity(new Intent(SignupActivity.this,UploadImage.class));
                                        finish();

                                        pgBarSignup.setVisibility(View.INVISIBLE);
                                    } else {
                                        String errors = task.getException()+"";
                                        Log.e("tag",errors);
                                        String ferror = errors.substring(errors.indexOf(":"));
                                        Toast.makeText(SignupActivity.this, "Error "+ferror, Toast.LENGTH_LONG).show();
                                        pgBarSignup.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });

                }
            }
        });





    }
}