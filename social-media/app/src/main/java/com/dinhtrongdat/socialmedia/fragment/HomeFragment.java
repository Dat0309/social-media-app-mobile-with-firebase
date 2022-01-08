package com.dinhtrongdat.socialmedia.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dinhtrongdat.socialmedia.R;
import com.dinhtrongdat.socialmedia.adapter.DashboardAdapter;
import com.dinhtrongdat.socialmedia.adapter.StoryAdapter;
import com.dinhtrongdat.socialmedia.model.Dashboard;
import com.dinhtrongdat.socialmedia.model.Post;
import com.dinhtrongdat.socialmedia.model.Story;
import com.dinhtrongdat.socialmedia.model.UserStoriees;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class HomeFragment extends Fragment {

    /**
     * View
     */
    RecyclerView storyRCV, dashboardRCV;
    StoryAdapter storyAdapter;
    DashboardAdapter dashboardAdapter;
    ImageView addStoryImage;
    ActivityResultLauncher<String> galleryLauncher;

    /**
     * Data base
     */
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
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
        storage = FirebaseStorage.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        storyRCV = view.findViewById(R.id.rcv_story);
        dashboardRCV = view.findViewById(R.id.rcv_newfeed);
        addStoryImage = view.findViewById(R.id.iv_add_story);

        initStory();
        initDashboard();

        addStoryImage.setOnClickListener(v->{
            galleryLauncher.launch("image/*");
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

                android.app.AlertDialog alertDialog = new SpotsDialog.Builder().setContext(getContext())
                        .setCancelable(false)
                        .setMessage("Chờ xíu")
                        .build();

                alertDialog.show();

                final StorageReference reference = storage.getReference()
                        .child("stories")
                        .child(FirebaseAuth.getInstance().getUid())
                        .child(String.valueOf(new Date().getTime()));

                reference.putFile(result).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Story story = new Story();
                                story.setStoryAt(new Date().getTime());

                                database.getReference()
                                        .child("stories")
                                        .child(FirebaseAuth.getInstance().getUid())
                                        .child("postedBy")
                                        .setValue(story.getStoryAt()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        UserStoriees storiees = new UserStoriees(uri.toString(), story.getStoryAt());

                                        database.getReference()
                                                .child("stories")
                                                .child(FirebaseAuth.getInstance().getUid())
                                                .child("userStories")
                                                .push()
                                                .setValue(storiees).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                alertDialog.dismiss();
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

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

        database.getReference().child("stories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listStory.size() != 0)
                    listStory.clear();
                if(snapshot.exists()){
                    for(DataSnapshot storySnapshot : snapshot.getChildren()){
                        Story story = new Story();
                        story.setStoryBy(storySnapshot.getKey());
                        story.setStoryAt(storySnapshot.child("postedBy").getValue(Long.class));

                        ArrayList<UserStoriees> stories = new ArrayList<>();
                        for(DataSnapshot snapshot1 : storySnapshot.child("userStories").getChildren()){
                            UserStoriees userStories = snapshot1.getValue(UserStoriees.class);

                            stories.add(userStories);
                        }
                        story.setStories(stories);
                        listStory.add(story);
                    }
                    storyAdapter = new StoryAdapter(getContext(), listStory);
                    storyRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                    storyRCV.setNestedScrollingEnabled(false);
                    storyRCV.setAdapter(storyAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}