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
import com.dinhtrongdat.socialmedia.model.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewHolder> {

    Context context;
    List<Notification> mdata;

    public NotificationAdapter(Context context, List<Notification> mdata) {
        this.context = context;
        this.mdata = mdata;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notify, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.viewHolder holder, int position) {
        Notification notification = mdata.get(position);

        holder.imgUser.setImageResource(notification.getProfile());
        holder.txtName.setText(notification.getName());
        holder.txtNotify.setText(notification.getNotification());
        holder.txtTime.setText(notification.getTime());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView imgUser;
        TextView txtName, txtNotify, txtTime;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.iv_user_notify);
            txtName = itemView.findViewById(R.id.tv_name_notify);
            txtNotify = itemView.findViewById(R.id.tv_notify);
            txtTime = itemView.findViewById(R.id.tv_time_notify);
        }
    }
}
