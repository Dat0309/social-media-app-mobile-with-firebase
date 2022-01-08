package com.dinhtrongdat.socialmedia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devlomi.circularstatusview.CircularStatusView;
import com.dinhtrongdat.socialmedia.R;
import com.dinhtrongdat.socialmedia.model.Story;
import com.dinhtrongdat.socialmedia.model.User;
import com.dinhtrongdat.socialmedia.model.UserStoriees;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.viewHolder> {

    Context context;
    List<Story> mdata;

    public StoryAdapter(Context context, List<Story> mdata) {
        this.context = context;
        this.mdata = mdata;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_story, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryAdapter.viewHolder holder, int position) {
        Story story = mdata.get(position);

        holder.statusView.setPortionsCount(story.getStories().size());

        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(story.getStoryBy()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Glide.with(context).load(user.getAvatar()).into(holder.imgStory);
                holder.txtName.setText(user.getUserName());

                holder.imgStory.setOnClickListener(v->{
                    List<MyStory> myStories = new ArrayList<>();

                    for(UserStoriees stories : story.getStories()){
                        myStories.add(new MyStory(
                           stories.getImage()
                        ));
                    }

                    new StoryView.Builder(((AppCompatActivity) context).getSupportFragmentManager())
                            .setStoriesList((ArrayList<MyStory>) myStories) // Required
                            .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
                            .setTitleText(user.getUserName()) // Default is Hidden
                            .setSubtitleText("") // Default is Hidden
                            .setTitleLogoUrl(user.getAvatar()) // Default is Hidden
                            .setStoryClickListeners(new StoryClickListeners() {
                                @Override
                                public void onDescriptionClickListener(int position) {
                                    //your action
                                }

                                @Override
                                public void onTitleIconClickListener(int position) {
                                    //your action
                                }
                            }) // Optional Listeners
                            .build() // Must be called before calling show method
                            .show();

                    
                });
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

        ImageView imgStory;
        TextView txtName;
        CircularStatusView statusView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            imgStory = itemView.findViewById(R.id.iv_avatar_story);
            txtName = itemView.findViewById(R.id.tv_name_story);
            statusView = itemView.findViewById(R.id.circular_status_view);
        }
    }
}
