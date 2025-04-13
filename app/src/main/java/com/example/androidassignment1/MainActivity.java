package com.example.androidassignment1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        setupAnimation();

        // Wait and open browsing activity
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(this, BrowseActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }

    private void setupAnimation() {
        Animation splashAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        ImageView logo = findViewById(R.id.splashLogo);

        logo.setAnimation(splashAnim);
    }
}