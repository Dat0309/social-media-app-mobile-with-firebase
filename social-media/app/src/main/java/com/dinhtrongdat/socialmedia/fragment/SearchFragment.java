package com.dinhtrongdat.socialmedia.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dinhtrongdat.socialmedia.R;
import com.dinhtrongdat.socialmedia.adapter.SearchUserAdapter;
import com.dinhtrongdat.socialmedia.databinding.FragmentSearchBinding;
import com.dinhtrongdat.socialmedia.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    FragmentSearchBinding binding;

    /**
     * View
     */
    SearchUserAdapter adapter;

    /**
     * Data
     */
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    FirebaseAuth auth;
    List<User> listUser;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        initUI();
        return binding.getRoot();
    }

    private void initUI() {
        listUser = new ArrayList<>();

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listUser.size()!=0)
                    listUser.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    user.setUserID(dataSnapshot.getKey());
                    if(!dataSnapshot.getKey().equals(FirebaseAuth.getInstance().getUid())){
                        listUser.add(user);
                    }
                }
                adapter = new SearchUserAdapter(getContext(), listUser);
                binding.rcvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.rcvSearch.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}