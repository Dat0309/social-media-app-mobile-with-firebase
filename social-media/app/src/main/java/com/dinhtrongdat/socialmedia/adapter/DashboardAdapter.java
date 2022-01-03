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
import com.dinhtrongdat.socialmedia.model.Dashboard;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.viewHolder> {

    Context context;
    List<Dashboard> mdata;

    public DashboardAdapter(Context context, List<Dashboard> mdata) {
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

        Dashboard dashboard = mdata.get(position);

        holder.imgAvatar.setImageResource(dashboard.getProfile());
        holder.imgPicture.setImageResource(dashboard.getPostImage());
        holder.txtName.setText(dashboard.getName());
        holder.txtLike.setText(dashboard.getLike() + " lượt thích");
        holder.txtStatus.setText(dashboard.getStatus());
        holder.txtCmt.setText("Xem tất cả " + dashboard.getComment() +" bình luận");
        holder.txtTime.setText(dashboard.getTime());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView imgAvatar, imgPicture;
        TextView txtName, txtLike, txtStatus, txtCmt, txtTime;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.iv_avt_dashboard);
            imgPicture = itemView.findViewById(R.id.iv_pic_dashboard);
            txtName = itemView.findViewById(R.id.tv_name_user_dashboard);
            txtLike = itemView.findViewById(R.id.tv_like);
            txtStatus = itemView.findViewById(R.id.tv_status);
            txtCmt = itemView.findViewById(R.id.tv_count_cmt);
            txtTime = itemView.findViewById(R.id.tv_time);
        }
    }
}
