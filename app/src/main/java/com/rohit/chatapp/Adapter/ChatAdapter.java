package com.rohit.chatapp.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.rohit.chatapp.Model.ChatModel;
import com.rohit.chatapp.Model.UserModel;
import com.rohit.chatapp.R;

public class ChatAdapter extends FirebaseRecyclerAdapter<ChatModel,ChatAdapter.ViewHolder> {

    Context context;
    String uid;
    String MyName,UrName;
    public ChatAdapter(@NonNull FirebaseRecyclerOptions<ChatModel> options,Context context,String uid,String MyName,String UrName) {
        super(options);
        this.context = context;
        this.uid = uid;
        this.MyName = MyName;
        this.UrName = UrName;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_chats,parent,false);
        return new ChatAdapter.ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ChatModel model) {
        if(model.getSender() == null){
            holder.itemMessageCard.setVisibility(View.GONE);
        }

        if(model.getSender() != null ){

            if(model.getSender().equals(uid)){
                holder.LinearRecieve.setVisibility(View.GONE);
                holder.txtMessageSender.setText(model.getMessage());
            }else{
                holder.LinearSend.setVisibility(View.GONE);
                holder.txtMessageRecieve.setText(model.getMessage());
            }
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMessageRecieve,txtMessageSender;
        LinearLayout itemMessageCard;

        LinearLayout LinearSend,LinearRecieve;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMessageRecieve = itemView.findViewById(R.id.txtMessageRecieve);
            txtMessageSender = itemView.findViewById(R.id.txtMessageSender);
            itemMessageCard = itemView.findViewById(R.id.itemMessageCard);
            LinearSend = itemView.findViewById(R.id.LinearSend);
            LinearRecieve = itemView.findViewById(R.id.LinearRecieve);
        }
    }
}
