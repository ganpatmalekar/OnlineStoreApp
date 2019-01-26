package com.example.swapnil.onlinestoreapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    private static int SPLASH_TIME_OUT = 2000;
    SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)this.findViewById(R.id.progress);
        sessionManagement = new SessionManagement(this);

        final HomeActivity homeActivity = new HomeActivity();
        progressBar.postDelayed(new Runnable() {
                @Override
                public void run() {

                    //homeActivity.startLogin();
                    if (sessionManagement.isLoggedIn()) {
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            },SPLASH_TIME_OUT);
    }
}
