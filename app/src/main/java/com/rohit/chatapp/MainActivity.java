package com.rohit.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rohit.chatapp.Adapter.UsersAdapter;
import com.rohit.chatapp.Model.UserModel;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UsersAdapter adapter;
    ImageView imageViewProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        imageViewProfile = findViewById(R.id.imageViewProfile);


        Query query = FirebaseDatabase.getInstance().getReference().child("Users");

        FirebaseRecyclerOptions<UserModel> options =  new FirebaseRecyclerOptions
                .Builder< UserModel >()
                .setQuery(query,UserModel.class)
                .build();




        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new UsersAdapter(options,MainActivity.this);
        recyclerView.setAdapter(adapter);

        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Signout", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}