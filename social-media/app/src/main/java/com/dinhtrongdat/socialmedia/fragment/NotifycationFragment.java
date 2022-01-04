package com.dinhtrongdat.socialmedia.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dinhtrongdat.socialmedia.R;
import com.dinhtrongdat.socialmedia.adapter.NotificationAdapter;
import com.dinhtrongdat.socialmedia.model.Notification;

import java.util.ArrayList;
import java.util.List;


public class NotifycationFragment extends Fragment {

    /**
     * View
     */
    RecyclerView rcvNotify;
    NotificationAdapter adapter;

    /**
     * Data
     */
    List<Notification> listData;

    public NotifycationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notifycation, container, false);

        rcvNotify = view.findViewById(R.id.rcv_notify);
        
        initNotify();

        return view;
    }

    private void initNotify() {
        listData = new ArrayList<>();
        listData.add(new Notification(R.drawable.dat, "dat0309","Đã thích bài viết của bạn", "1 giấy"));
        listData.add(new Notification(R.drawable.dat, "dat0309","Đã thích bài viết của bạn", "1 giấy"));
        listData.add(new Notification(R.drawable.dat, "dat0309","Đã thích bài viết của bạn", "1 giấy"));
        listData.add(new Notification(R.drawable.dat, "dat0309","Đã thích bài viết của bạn", "1 giấy"));
        listData.add(new Notification(R.drawable.dat, "dat0309","Đã thích bài viết của bạn", "1 giấy"));
        listData.add(new Notification(R.drawable.dat, "dat0309","Đã thích bài viết của bạn", "1 giấy"));
        listData.add(new Notification(R.drawable.dat, "dat0309","Đã thích bài viết của bạn", "1 giấy"));

        adapter = new NotificationAdapter(getContext(), listData);
        rcvNotify.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcvNotify.setNestedScrollingEnabled(false);
        rcvNotify.setAdapter(adapter);
    }
}