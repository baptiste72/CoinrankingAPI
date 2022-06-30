package com.example.coinrankingapi.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.coinrankingapi.CustomWindow;
import com.example.coinrankingapi.R;

public class StartActivity extends CustomWindow {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final int startActivityTimeout = 2000;

        new Handler().postDelayed(() -> {
            startMainActivity();
            finish();
        }, startActivityTimeout);
    }

    /**
     * Lance l'activité MainActivity
     */
    private void startMainActivity() {
        final Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }
}