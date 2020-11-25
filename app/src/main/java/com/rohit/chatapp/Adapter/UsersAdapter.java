package com.rohit.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.rohit.chatapp.ChatActivity;
import com.rohit.chatapp.Model.UserModel;
import com.rohit.chatapp.R;
import com.rohit.chatapp.SplashScreen;

public class UsersAdapter extends FirebaseRecyclerAdapter<UserModel,UsersAdapter.ViewHolder> {

    Context context;
    public UsersAdapter(@NonNull FirebaseRecyclerOptions<UserModel> options,Context context) {
        super(options);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_users,parent,false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull final UserModel model) {

        holder.txtItemUser.setText(model.getUsername());

        if(model.getImg() != null){
            Glide.with(context)
                    .load(model.getImg())
                    .into(holder.imgItemUser);
        }else{
            holder.imgItemUser.setImageResource(R.drawable.circle_cropped);
        }

        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("Username",model.getUsername());
                intent.putExtra("MyName", SplashScreen.MyName);
                intent.putExtra("uid",model.getUid());
                intent.putExtra("uid2",FirebaseAuth.getInstance().getUid());
                intent.putExtra("url",model.getImg()) ;
                context.startActivity(intent);

            }
        });


    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgItemUser;
        TextView txtItemUser;
        CardView itemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItemUser = itemView.findViewById(R.id.imgItemUser);
            txtItemUser = itemView.findViewById(R.id.txtItemUser);
            itemLayout = itemView.findViewById(R.id.itemLayout);

        }
    }
}
