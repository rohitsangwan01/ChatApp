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

public class LoginActivity extends AppCompatActivity {

    EditText usernameLogin,passwordLogin;
    ProgressBar pgBarLogin;
    TextView txtForgetPassword,btnLogin,txtSignup;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameLogin = findViewById(R.id.usernameLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        pgBarLogin = findViewById(R.id.pgBarLogin);
        txtForgetPassword = findViewById(R.id.txtForgetPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignup = findViewById(R.id.txtSignup);


        pgBarLogin.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usernameLogin.getText().toString().equals("")){
                    usernameLogin.setError("Enter Usrname");
                }else if(passwordLogin.getText().toString().equals("")){
                    passwordLogin.setError("Enter Password");
                }else{

                    pgBarLogin.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(usernameLogin.getText().toString(), passwordLogin.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                        pgBarLogin.setVisibility(View.GONE);

                                    } else {
                                        String errors = task.getException()+"";
                                        Log.e("tag",errors);
                                        pgBarLogin.setVisibility(View.GONE);

                                        if(errors.contains("com.google.firebase.auth.FirebaseAuthInvalidUserException")){
                                            Toast.makeText(LoginActivity.this, "Please Signup First", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this,SignupActivity.class));
                                            finish();
                                        }else{
                                            String ferror = errors.substring(errors.indexOf(":"));
                                            Toast.makeText(LoginActivity.this, "Error "+ferror, Toast.LENGTH_LONG).show();
                                        }

                                    }
                                }
                            });
                }
            }
        });






        txtForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usernameLogin.getText().toString().equals("")){
                    usernameLogin.setError("Enter Email");
                }else{
                    pgBarLogin.setVisibility(View.VISIBLE);
                    FirebaseAuth.getInstance().sendPasswordResetEmail(usernameLogin.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        pgBarLogin.setVisibility(View.GONE);
                                        Toast.makeText(LoginActivity.this, "Email Sent To Reset Password", Toast.LENGTH_SHORT).show();
                                    }else{
                                        String errors = task.getException()+"";
                                        Log.e("tag",errors);
                                        String ferror = errors.substring(errors.indexOf(":"));
                                        Toast.makeText(LoginActivity.this, "Error "+ferror, Toast.LENGTH_LONG).show();
                                        pgBarLogin.setVisibility(View.GONE);
                                    }
                                }
                            });
                }

            }
        });

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }

    }
}