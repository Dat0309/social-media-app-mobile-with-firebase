package com.dinhtrongdat.socialmedia.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.dinhtrongdat.socialmedia.LoginAct;
import com.dinhtrongdat.socialmedia.R;
import com.dinhtrongdat.socialmedia.databinding.FragmentAddBinding;
import com.dinhtrongdat.socialmedia.databinding.FragmentSearchBinding;
import com.dinhtrongdat.socialmedia.model.Post;
import com.dinhtrongdat.socialmedia.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;

import dmax.dialog.SpotsDialog;

public class AddFragment extends Fragment implements View.OnClickListener {

    FragmentAddBinding binding;

    /**
     * Data
     */
    Uri uri;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false);

        fetchDataUser();
        initUI();

        return binding.getRoot();
    }

    private void fetchDataUser() {
        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    User user = snapshot.getValue(User.class);
                    Glide.with(getContext()).load(user.getAvatar()).into(binding.ivAvtPost);
                    binding.tvNameUserPost.setText(user.getUserName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initUI() {
        binding.layoutAddMore.setOnClickListener(this);
        binding.btnPost.setOnClickListener(this);

        binding.postDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String description = binding.postDescription.getText().toString();
                if(!description.isEmpty()){
                    binding.btnPost.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_button));
                    binding.btnPost.setTextColor(getContext().getResources().getColor(R.color.white));
                    binding.btnPost.setEnabled(true);
                }else{
                    binding.btnPost.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_button_activate));
                    binding.btnPost.setTextColor(getContext().getResources().getColor(R.color.gray));
                    binding.btnPost.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_add_more:
                showDialog();
                break;
            case R.id.btn_post:
                postImage();
                break;
        }
    }

    private void postImage() {
        android.app.AlertDialog alertDialog = new SpotsDialog.Builder().setContext(getContext())
                .setCancelable(false)
                .setMessage("Chờ xíu")
                .build();

        alertDialog.show();

        final StorageReference reference = storage.getReference().child("posts")
                .child(FirebaseAuth.getInstance().getUid())
                .child(new Date().getTime()+"");
        reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Post post = new Post();
                        post.setPostImage(uri.toString());
                        post.setPostedBy(FirebaseAuth.getInstance().getUid());
                        post.setPosrDescription(binding.postDescription.getText().toString());
                        post.setPostedAt(new Date().getTime());
                        post.setPostLike(0);
                        post.setCommentCount(0);

                        database.getReference().child("posts")
                                .push()
                                .setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                alertDialog.dismiss();
                                FragmentTransaction fr = getFragmentManager().beginTransaction();
                                fr.replace(R.id.container,new HomeFragment());
                                fr.commit();
                            }
                        });
                    }
                });
            }
        });
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_post_layout);

        LinearLayout addPhoto = dialog.findViewById(R.id.addphoto);
        LinearLayout tagPerson = dialog.findViewById(R.id.tagperson);
        LinearLayout emoji = dialog.findViewById(R.id.emoji);
        LinearLayout checkin = dialog.findViewById(R.id.checkin);
        LinearLayout stream = dialog.findViewById(R.id.stream);

        addPhoto.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 10);
            dialog.dismiss();
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
        if(data.getData() != null){
            uri = data.getData();

            binding.postImage.setImageURI(uri);
            binding.postImage.setVisibility(View.VISIBLE);

            binding.btnPost.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_button));
            binding.btnPost.setTextColor(getContext().getResources().getColor(R.color.white));
            binding.btnPost.setEnabled(true);
        }
    }
}