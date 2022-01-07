package com.dinhtrongdat.socialmedia.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dinhtrongdat.socialmedia.CommentAct;
import com.dinhtrongdat.socialmedia.R;
import com.dinhtrongdat.socialmedia.model.Dashboard;
import com.dinhtrongdat.socialmedia.model.Post;
import com.dinhtrongdat.socialmedia.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.viewHolder> {

    Context context;
    List<Post> mdata;

    public DashboardAdapter(Context context, List<Post> mdata) {
        this.context = context;
        this.mdata = mdata;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dashboard, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardAdapter.viewHolder holder, int position) {

        Post post = mdata.get(position);
        Glide.with(context).load(post.getPostImage()).into(holder.imgPicture);
        holder.txtLike.setText(String.valueOf(post.getPostLike()) + " lượt thích");

        if(post.getCommentCount() == 0)
            holder.txtCmtCount.setVisibility(View.GONE);
        else
            holder.txtCmtCount.setText("Xem tất cả "+ String.valueOf(post.getCommentCount()) + " bình luận");

        if(post.getPosrDescription().equals(""))
            holder.txtStatus.setVisibility(View.GONE);
        else
            holder.txtStatus.setText(post.getPosrDescription());

        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(post.getPostedBy()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Glide.with(context).load(user.getAvatar()).into(holder.imgAvatar);
                holder.txtName.setText(user.getUserName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Glide.with(context).load(user.getAvatar()).into(holder.imgAvatarCmt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference()
                .child("posts")
                .child(post.getPostId())
                .child("likes")
                .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    holder.btnLike.setImageResource(R.drawable.ic_favorite_activate);
                    holder.btnLike.setOnClickListener(v->{
                        FirebaseDatabase.getInstance().getReference()
                                .child("posts")
                                .child(post.getPostId())
                                .child("likes")
                                .child(FirebaseAuth.getInstance().getUid())
                                .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                FirebaseDatabase.getInstance().getReference()
                                        .child("posts")
                                        .child(post.getPostId())
                                        .child("postLike")
                                        .setValue(post.getPostLike() - 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        holder.btnLike.setImageResource(R.drawable.ic_favorite);
                                    }
                                });
                            }
                        });
                    });
                }else{
                    holder.btnLike.setOnClickListener(v->{
                        FirebaseDatabase.getInstance().getReference()
                                .child("posts")
                                .child(post.getPostId())
                                .child("likes")
                                .child(FirebaseAuth.getInstance().getUid())
                                .setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                FirebaseDatabase.getInstance().getReference()
                                        .child("posts")
                                        .child(post.getPostId())
                                        .child("postLike")
                                        .setValue(post.getPostLike() + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        holder.btnLike.setImageResource(R.drawable.ic_favorite_activate);
                                    }
                                });
                            }
                        });
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.btnCmt.setOnClickListener(v->{
            Intent intent = new Intent(context, CommentAct.class);
            intent.putExtra("post", post);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView imgAvatar, imgPicture, imgAvatarCmt;
        TextView txtName, txtLike, txtStatus, txtCmtCount, txtTime;
        ImageView btnLike, btnCmt, btnShare;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.iv_avt_dashboard);
            imgPicture = itemView.findViewById(R.id.iv_pic_dashboard);
            txtName = itemView.findViewById(R.id.tv_name_user_dashboard);
            txtLike = itemView.findViewById(R.id.tv_like);
            txtStatus = itemView.findViewById(R.id.tv_status);
            txtCmtCount = itemView.findViewById(R.id.tv_count_cmt);
            txtTime = itemView.findViewById(R.id.tv_time);
            btnLike = itemView.findViewById(R.id.iv_like);
            btnCmt = itemView.findViewById(R.id.iv_cmt);
            btnShare = itemView.findViewById(R.id.iv_send);
            imgAvatarCmt = itemView.findViewById(R.id.iv_user_cmt);
        }
    }
}
