package com.dinhtrongdat.socialmedia.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dinhtrongdat.socialmedia.LoginAct;
import com.dinhtrongdat.socialmedia.R;
import com.dinhtrongdat.socialmedia.model.User;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Currency;
import java.util.Objects;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    int UPLOAD_AVATAR = 11;
    int UPLOAD_COVER = 12;

    /**
     * Database
     */
    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;

    /**
     *
     * View
     */
    KenBurnsView imgCover;
    ImageView imgAvatar;
    TextView userName, description, followers, following;
    ImageView changeAvatar, changeCover, btnMenu;

    public ProfileFragment() {
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initUI(view);

        return view;
    }

    private void initUI(View view) {

        imgAvatar = view.findViewById(R.id.avatar);
        imgCover = view.findViewById(R.id.cover);
        userName = view.findViewById(R.id.tv_username);
        changeAvatar = view.findViewById(R.id.btn_change_avatar);
        changeCover = view.findViewById(R.id.btn_change_cover);
        description = view.findViewById(R.id.description);
        followers = view.findViewById(R.id.tv_followers);
        btnMenu = view.findViewById(R.id.btn_menu);
        following = view.findViewById(R.id.tv_following);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        database.getReference().child("Users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User user = snapshot.getValue(User.class);
                    Glide.with(ProfileFragment.this).load(user.getAvatar()).into(imgAvatar);
                    Glide.with(ProfileFragment.this).load(user.getCover()).into(imgCover);
                    userName.setText(user.getUserName());
                    description.setText(user.getAbout());
                    followers.setText(String.valueOf(user.getFollowerCount()));
                    following.setText(String.valueOf(user.getFollowingCount()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        changeCover.setOnClickListener(this);
        changeAvatar.setOnClickListener(this);
        btnMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_change_avatar:
                intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,UPLOAD_AVATAR);
                break;
            case R.id.btn_change_cover:
                intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,UPLOAD_COVER);
                break;
            case R.id.btn_menu:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_layout);

        LinearLayout logoutLayout = dialog.findViewById(R.id.layout_logout);

        logoutLayout.setOnClickListener(v->{
            auth.signOut();
            startActivity(new Intent(getContext(), LoginAct.class));
            if(dialog != null)
                dialog.dismiss();
            getActivity().finish();
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData()!=null && requestCode == UPLOAD_AVATAR){
            Uri uri = data.getData();
            imgAvatar.setImageURI(uri);

            final StorageReference reference = storage.getReference().child("avatar_user").child(auth.getUid());
            reference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                Toast.makeText(getContext(), "Đã cập nhật ảnh đại diện", Toast.LENGTH_SHORT).show();
                reference.getDownloadUrl().addOnSuccessListener(uri1 ->
                        database.getReference().child("Users").child(auth.getUid()).child("avatar").setValue(uri1.toString()));
            });
        }
        else if(data.getData()!=null && requestCode == UPLOAD_COVER){
            Uri uri = data.getData();
            imgCover.setImageURI(uri);

            final StorageReference reference = storage.getReference().child("cover_user").child(auth.getUid());
            reference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                Toast.makeText(getContext(), "Đã cập nhật ảnh bìa thành công", Toast.LENGTH_SHORT).show();
                reference.getDownloadUrl().addOnSuccessListener(uri1 ->
                        database.getReference().child("Users").child(auth.getUid()).child("cover").setValue(uri1.toString()));
            });
        }
    }
}