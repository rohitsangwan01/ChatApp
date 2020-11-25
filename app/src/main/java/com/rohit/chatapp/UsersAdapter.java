package com.rohit.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

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
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull UserModel model) {

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
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
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
