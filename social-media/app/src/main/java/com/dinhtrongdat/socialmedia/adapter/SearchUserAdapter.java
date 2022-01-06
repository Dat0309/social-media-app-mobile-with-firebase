package com.dinhtrongdat.socialmedia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dinhtrongdat.socialmedia.R;
import com.dinhtrongdat.socialmedia.model.Follow;
import com.dinhtrongdat.socialmedia.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.List;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.viewHolder> {

    Context context;
    List<User> mdata;

    public SearchUserAdapter(Context context, List<User> mdata) {
        this.context = context;
        this.mdata = mdata;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchUserAdapter.viewHolder holder, int position) {
        User user = mdata.get(position);

        Glide.with(context).load(user.getAvatar()).into(holder.imgUser);
        holder.txtName.setText(user.getName());

        holder.btnFollow.setOnClickListener(v->{
            Follow follow = new Follow();
            follow.setFollowedBy(FirebaseAuth.getInstance().getUid());
            follow.setFollowedAt(new Date().getTime());

            FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(user.getUserID())
                    .child("followers")
                    .child(FirebaseAuth.getInstance().getUid())
                    .setValue(follow).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(user.getUserID())
                            .child("followerCount")
                            .setValue(user.getFollowerCount() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            
                        }
                    });
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView imgUser;
        TextView txtName;
        Button btnFollow;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.iv_user_search);
            txtName = itemView.findViewById(R.id.tv_name_user_search);
            btnFollow = itemView.findViewById(R.id.btn_follow);
        }
    }
}
