package com.dinhtrongdat.socialmedia.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dinhtrongdat.socialmedia.R;
import com.dinhtrongdat.socialmedia.adapter.DashboardAdapter;
import com.dinhtrongdat.socialmedia.adapter.StoryAdapter;
import com.dinhtrongdat.socialmedia.model.Dashboard;
import com.dinhtrongdat.socialmedia.model.Story;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    /**
     * View
     */
    RecyclerView storyRCV, dashboardRCV;
    StoryAdapter storyAdapter;
    DashboardAdapter dashboardAdapter;

    /**
     * Data base
     */
    List<Story> listStory;
    List<Dashboard> listDasboard;

    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        storyRCV = view.findViewById(R.id.rcv_story);
        dashboardRCV = view.findViewById(R.id.rcv_newfeed);

        initStory();
        initDashboard();

        return view;
    }

    private void initDashboard() {
        listDasboard = new ArrayList<>();
        listDasboard.add(new Dashboard(R.drawable.dat,R.drawable.dat,"dat0309","HELLO WORLD", "6918", "9", "Vừa xong"));
        listDasboard.add(new Dashboard(R.drawable.dat,R.drawable.dat,"dat0309","HELLO WORLD", "6918", "9", "Vừa xong"));
        listDasboard.add(new Dashboard(R.drawable.dat,R.drawable.dat,"dat0309","HELLO WORLD", "6918", "9", "Vừa xong"));
        listDasboard.add(new Dashboard(R.drawable.dat,R.drawable.dat,"dat0309","HELLO WORLD", "6918", "9", "Vừa xong"));
        listDasboard.add(new Dashboard(R.drawable.dat,R.drawable.dat,"dat0309","HELLO WORLD", "6918", "9", "Vừa xong"));

        dashboardAdapter = new DashboardAdapter(getContext(), listDasboard);
        dashboardRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        dashboardRCV.setNestedScrollingEnabled(false);
        dashboardRCV.setAdapter(dashboardAdapter);
    }

    private void initStory() {
        listStory = new ArrayList<>();
        listStory.add(new Story(0,0,R.drawable.dat,"Trong Dat"));
        listStory.add(new Story(0,0,R.drawable.dat,"Trong Dat"));
        listStory.add(new Story(0,0,R.drawable.dat,"Trong Dat"));
        listStory.add(new Story(0,0,R.drawable.dat,"Trong Dat"));
        listStory.add(new Story(0,0,R.drawable.dat,"Trong Dat"));
        listStory.add(new Story(0,0,R.drawable.dat,"Trong Dat"));
        listStory.add(new Story(0,0,R.drawable.dat,"Trong Dat"));
        listStory.add(new Story(0,0,R.drawable.dat,"Trong Dat"));

        storyAdapter = new StoryAdapter(getContext(), listStory);
        storyRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        storyRCV.setNestedScrollingEnabled(false);
        storyRCV.setAdapter(storyAdapter);
    }
}