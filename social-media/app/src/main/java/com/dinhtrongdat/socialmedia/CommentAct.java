package com.dinhtrongdat.socialmedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.dinhtrongdat.socialmedia.adapter.CommentAdapter;
import com.dinhtrongdat.socialmedia.databinding.ActivityCommentBinding;
import com.dinhtrongdat.socialmedia.model.Comment;
import com.dinhtrongdat.socialmedia.model.Post;
import com.dinhtrongdat.socialmedia.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentAct extends AppCompatActivity {

    ActivityCommentBinding binding;
    Post curPost;

    /**
     * Data
     */
    FirebaseDatabase database;
    FirebaseAuth auth;
    CommentAdapter adapter;
    List<Comment> listCmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        curPost = (Post) intent.getSerializableExtra("post");

        database.getReference().child("Users").child(curPost.getPostedBy()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.ivPostCmt.setOnClickListener(v->{
            Comment comment = new Comment();
            comment.setCommentBody(binding.edtPostCmt.getText().toString());
            comment.setCommentedAt(new Date().getTime());
            comment.setCommentedBy(FirebaseAuth.getInstance().getUid());

            database.getReference().child("posts").child(curPost.getPostId()).child("comments").push()
                    .setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    database.getReference()
                            .child("posts")
                            .child(curPost.getPostId())
                            .child("commentCount")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int cmtCount = 0;
                                    if(snapshot.exists()){
                                        cmtCount = snapshot.getValue(Integer.class);

                                    }
                                    database.getReference()
                                            .child("posts")
                                            .child(curPost.getPostId())
                                            .child("commentCount")
                                            .setValue(cmtCount + 1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            binding.edtPostCmt.setText("");
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
            });
        });

        listCmt = new ArrayList<>();

        database.getReference().child("posts").child(curPost.getPostId()).child("comments")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(listCmt.size() != 0)
                            listCmt.clear();
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Comment comment = dataSnapshot.getValue(Comment.class);
                            listCmt.add(comment);
                        }
                        adapter = new CommentAdapter(CommentAct.this, listCmt);
                        adapter.notifyDataSetChanged();
                        binding.rcvCmt.setLayoutManager(new LinearLayoutManager(CommentAct.this));
                        binding.rcvCmt.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.ivBackCmt.setOnClickListener(v->{
            finish();
        });
    }
}