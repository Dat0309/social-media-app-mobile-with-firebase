package com.dinhtrongdat.socialmedia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinhtrongdat.socialmedia.R;
import com.dinhtrongdat.socialmedia.model.Story;

import java.util.ArrayList;
import java.util.List;

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
        holder.txtName.setText(story.getName());
        holder.imgStory.setImageResource(story.getProfile());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView imgStory;
        TextView txtName;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            imgStory = itemView.findViewById(R.id.iv_avatar_story);
            txtName = itemView.findViewById(R.id.tv_name_story);
        }
    }
}
