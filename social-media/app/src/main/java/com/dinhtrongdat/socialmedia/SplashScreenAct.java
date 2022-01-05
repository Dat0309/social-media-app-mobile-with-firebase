package com.dinhtrongdat.socialmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreenAct extends AppCompatActivity {

    LottieAnimationView splash;
    Animation layoutAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        layoutAnimation = AnimationUtils.loadAnimation(SplashScreenAct.this, R.anim.anim_fall_down);
        splash = findViewById(R.id.splash_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                splash.setVisibility(View.VISIBLE);
                splash.setAnimation(layoutAnimation);
            }
        }, 500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isLogin();
            }
        }, 6000);
    }

    private void isLogin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            Intent intent = new Intent(SplashScreenAct.this, LoginAct.class);

            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(SplashScreenAct.this, MainActivity.class);

            startActivity(intent);
            finish();
        }
    }

}