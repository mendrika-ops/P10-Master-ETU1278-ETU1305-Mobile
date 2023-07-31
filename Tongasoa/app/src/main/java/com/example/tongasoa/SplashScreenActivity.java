package com.example.tongasoa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 5000;
    Animation boutom, top;
    ImageView image;
    TextView logo,slogan;
    ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        //spinner=(ProgressBar)findViewById(R.id.progressBar);
        //spinner.setVisibility(View.GONE);
        //spinner.setVisibility(View.VISIBLE);
        boutom = AnimationUtils.loadAnimation(this, R.anim.animation_boutom);
        top = AnimationUtils.loadAnimation(this, R.anim.animation_top);

        //image = findViewById(R.id.imageView);
        //slogan = findViewById(R.id.slogan);
        //image.setAnimation(top);
        //slogan.setAnimation(boutom);

        new Handler().postDelayed(()-> {

            Intent intent = new Intent(SplashScreenActivity.this, MyHome.class);
            startActivity(intent);
            finish();
        },SPLASH_SCREEN);
    }
}