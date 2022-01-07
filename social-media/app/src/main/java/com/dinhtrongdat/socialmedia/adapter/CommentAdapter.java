package com.dinhtrongdat.socialmedia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dinhtrongdat.socialmedia.R;
import com.dinhtrongdat.socialmedia.model.Comment;
import com.dinhtrongdat.socialmedia.model.User;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.viewHolder> {

    Context context;
    List<Comment> mdata;

    public CommentAdapter(Context context, List<Comment> mdata) {
        this.context = context;
        this.mdata = mdata;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.viewHolder holder, int position) {
        Comment comment = mdata.get(position);
        holder.txtComment.setText(comment.getCommentBody());

        String time = TimeAgo.using(comment.getCommentedAt());
        holder.txtTime.setText(time);

        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(comment.getCommentedBy()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Glide.with(context).load(user.getAvatar()).into(holder.imgUser);
                holder.txtName.setText(user.getUserName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView imgUser;
        TextView txtName, txtComment, txtTime;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            imgUser = itemView.findViewById(R.id.iv_user_cmt);
            txtName = itemView.findViewById(R.id.tv_name_cmt);
            txtComment = itemView.findViewById(R.id.tv_description_cmt);
            txtTime = itemView.findViewById(R.id.tv_time_cmt);
        }
    }
}
