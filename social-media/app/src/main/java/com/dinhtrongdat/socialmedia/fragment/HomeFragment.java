package com.dinhtrongdat.socialmedia.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dinhtrongdat.socialmedia.R;
import com.dinhtrongdat.socialmedia.adapter.DashboardAdapter;
import com.dinhtrongdat.socialmedia.adapter.StoryAdapter;
import com.dinhtrongdat.socialmedia.model.Dashboard;
import com.dinhtrongdat.socialmedia.model.Post;
import com.dinhtrongdat.socialmedia.model.Story;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    FirebaseDatabase database;
    FirebaseAuth auth;
    List<Story> listStory;
    List<Post> listDasboard;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
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

    /**
     * Load data dashboard
     */
    private void initDashboard() {
        listDasboard = new ArrayList<>();


        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listDasboard.size() != 0)
                    listDasboard.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    post.setPostId(dataSnapshot.getKey());

                    database.getReference().child("Users").child(auth.getUid()).child("following").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataPost : snapshot.getChildren()) {
                                if (post.getPostedBy().equals(dataPost.getKey()) || post.getPostedBy().equals(auth.getUid())) {
                                    listDasboard.add(post);
                                }
                                dashboardAdapter = new DashboardAdapter(getContext(), listDasboard);
                                dashboardRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                                dashboardRCV.setNestedScrollingEnabled(false);
                                dashboardRCV.setAdapter(dashboardAdapter);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    /**
     * Load data story
     */
    private void initStory() {
        listStory = new ArrayList<>();
        listStory.add(new Story(0, 0, R.drawable.dat, "Trong Dat"));
        listStory.add(new Story(0, 0, R.drawable.dat, "Trong Dat"));
        listStory.add(new Story(0, 0, R.drawable.dat, "Trong Dat"));
        listStory.add(new Story(0, 0, R.drawable.dat, "Trong Dat"));
        listStory.add(new Story(0, 0, R.drawable.dat, "Trong Dat"));
        listStory.add(new Story(0, 0, R.drawable.dat, "Trong Dat"));
        listStory.add(new Story(0, 0, R.drawable.dat, "Trong Dat"));
        listStory.add(new Story(0, 0, R.drawable.dat, "Trong Dat"));

        storyAdapter = new StoryAdapter(getContext(), listStory);
        storyRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        storyRCV.setNestedScrollingEnabled(false);
        storyRCV.setAdapter(storyAdapter);
    }
}