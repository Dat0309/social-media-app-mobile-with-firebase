package com.dinhtrongdat.socialmedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.dinhtrongdat.socialmedia.databinding.ActivityMainBinding;
import com.dinhtrongdat.socialmedia.fragment.AddFragment;
import com.dinhtrongdat.socialmedia.fragment.HomeFragment;
import com.dinhtrongdat.socialmedia.fragment.NotifycationFragment;
import com.dinhtrongdat.socialmedia.fragment.ProfileFragment;
import com.dinhtrongdat.socialmedia.fragment.SearchFragment;
import com.iammert.library.readablebottombar.ReadableBottomBar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new HomeFragment());
        transaction.commit();

        initUI();
    }

    private void initUI() {
        binding.readableBottomBar.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
            @Override
            public void onItemSelected(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                switch (i){
                    case 0:
                        transaction.replace(R.id.container, new HomeFragment());
                        break;
                    case 1:
                        transaction.replace(R.id.container, new NotifycationFragment());
                        break;
                    case 2:
                        transaction.replace(R.id.container, new AddFragment());
                        break;
                    case 3:
                        transaction.replace(R.id.container, new SearchFragment());
                        break;
                    case 4:
                        transaction.replace(R.id.container, new ProfileFragment());
                        break;
                }
                transaction.commit();
            }
        });
    }
}