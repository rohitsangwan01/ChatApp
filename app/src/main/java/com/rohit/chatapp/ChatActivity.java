package com.rohit.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rohit.chatapp.Adapter.ChatAdapter;
import com.rohit.chatapp.Adapter.UsersAdapter;
import com.rohit.chatapp.Model.ChatModel;

import java.util.ArrayList;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    TextView txtChatUser;
    CircleImageView imgUserChat;
    ProgressBar pgBarChatting;
    RecyclerView recyclerView;
    ChatAdapter adapter;

    EditText edtEnterMessage;
    ImageView btnSend;
    String ChatRoomUser = "";
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        txtChatUser = findViewById(R.id.txtChatUser);
        imgUserChat = findViewById(R.id.imgUserChat);
        pgBarChatting = findViewById(R.id.pgBarChatting);
        recyclerView = findViewById(R.id.recyclerChat);
        edtEnterMessage = findViewById(R.id.edtEnterMessage);
        btnSend = findViewById(R.id.btnSend);

        final String username = getIntent().getStringExtra("Username");
        String url = getIntent().getStringExtra("url");
        final String MyName = SplashScreen.MyName;

        //uid1 is uid of Another person and uid2 is my uid
        final String uid1 = getIntent().getStringExtra("uid");
        final String uid2 = getIntent().getStringExtra("uid2");

        if(uid1.equals(uid2)){
            Toast.makeText(this, "Why You Want To Chat With Yourself Buddy", Toast.LENGTH_SHORT).show();
            finish();
        }

        Log.e("uid",uid1+" + "+uid2);

        btnSend.setEnabled(false);
        edtEnterMessage.setEnabled(false);

        FirebaseDatabase.getInstance().getReference().child("Chats").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList childList = new ArrayList();
                String ChatRoom = null;
                for(DataSnapshot sp : snapshot.getChildren()){
                    if(sp.getKey().contains(uid1) && sp.getKey().contains(uid2)){
                        pgBarChatting.setVisibility(View.INVISIBLE);
                        ChatRoom = sp.getKey();
                    }
                }

                if(ChatRoom != null){
                    ChatRoomUser = ChatRoom;

                    DatabaseReference ChatRef = FirebaseDatabase.getInstance().getReference().child("Chats").child(ChatRoom);

                    Query query = FirebaseDatabase.getInstance().getReference().child("Chats").child(ChatRoom)
                            .child("Chatting");

                    FirebaseRecyclerOptions<ChatModel> options1 =  new FirebaseRecyclerOptions
                            .Builder< ChatModel >()
                            .setQuery(query,ChatModel.class)
                            .build();
                    String mName = MyName;
                    String uName = username;

                     linearLayoutManager = new LinearLayoutManager(ChatActivity.this);
                   // linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    adapter = new ChatAdapter(options1,ChatActivity.this,uid2,mName,uName);
                    recyclerView.setAdapter(adapter);

                    adapter.startListening();

                }else{
                    ChatRoomUser = "";
                    pgBarChatting.setVisibility(View.INVISIBLE);
                    Toast.makeText(ChatActivity.this, "No Chats", Toast.LENGTH_SHORT).show();
                }



                btnSend.setEnabled(true);
                edtEnterMessage.setEnabled(true);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Glide.with(ChatActivity.this).load(url).into(imgUserChat);

        txtChatUser.setText(username);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtEnterMessage.getText().toString().equals("")){
                    btnSend.setEnabled(false);
                    edtEnterMessage.setEnabled(false);

                 //   linearLayoutManager.setStackFromEnd(true);

                    if(ChatRoomUser.equals("")){
                        String room = uid1+"+"+uid2;
                        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference().child("Chats").child(room);
                        String Random = "1";
                        chatRef.child("CurrentMessages").child("number").setValue("1");

                        chatRef.child("Chatting").child(Random).child("sender").setValue(uid2);
                        chatRef.child("Chatting").child(Random).child("number").setValue("1");
                        chatRef.child("Chatting").child(Random).child("message").setValue(edtEnterMessage.getText().toString());

                        btnSend.setEnabled(true);
                        edtEnterMessage.setEnabled(true);
                        edtEnterMessage.setText("");
                    }else{
                        String room = ChatRoomUser;
                        final DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference().child("Chats").child(room);


                        chatRef.child("CurrentMessages").child("number").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int number = 1+ (Integer.parseInt(snapshot.getValue()+""));

                                String Random = number+"";
                                chatRef.child("Chatting").child(Random).child("sender").setValue(uid2);
                                chatRef.child("Chatting").child(Random).child("message").setValue(edtEnterMessage.getText().toString());
                                chatRef.child("Chatting").child(Random).child("number").setValue(number+"");

                                chatRef.child("CurrentMessages").child("number").setValue(number+"");
                                btnSend.setEnabled(true);
                                edtEnterMessage.setEnabled(true);
                                edtEnterMessage.setText("");
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                    }
                }
            }
        });

    }
}