package com.example.a1180887_suliman_izz;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView backgroundImage;
    private int[] backgroundImages = {R.drawable.day, R.drawable.night};
    private int count = 0;
    private Handler cloud1Handler = new Handler();
    private Handler cloud2Handler = new Handler();

    int fadeInDuration = 3000; // Configure time values here
    int fadeOutDuration = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageMoon = findViewById(R.id.moon);
        final ImageView imageSun = findViewById(R.id.sun);
        backgroundImage = findViewById(R.id.backGround_image);


        imageMoon.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.moon));
        imageSun.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.sun));
        startCloudAnimation(); // for the first cloud
        startCloudAnimation2();// for the second cloud
        switchBackgroundImage();// for the Background
    }

    private void startCloudAnimation() {
        final ImageView imageCloud1 = findViewById(R.id.cloud1);
        imageCloud1.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.cloud1));
        cloud1Handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startCloudAnimation();
            }
        }, 8000); // 8 seconds
    }

    private void startCloudAnimation2() {
        final ImageView imageCloud2 = findViewById(R.id.cloud2);
        imageCloud2.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.cloud2));
        cloud2Handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startCloudAnimation2();
            }
        }, 12000);// 12 seconds
    }

    private void switchBackgroundImage() {
        // Change the background image
        backgroundImage.setImageResource(backgroundImages[count]);

        // Fade in the new image
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(fadeInDuration);

        // Fade out the current image after the fadeIn animation
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(fadeInDuration + fadeOutDuration);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(2);

        // Set up the animation listener
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                // Increment the index or reset to 0 if at the end
                count = (count + 1) % backgroundImages.length;

                // Call the switchBackgroundImage function recursively
                switchBackgroundImage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Not needed for this case
            }

            @Override
            public void onAnimationStart(Animation animation) {
                // Not needed for this case
            }
        });

        // Apply the animation to the image view
        backgroundImage.startAnimation(animation);
    }
}
