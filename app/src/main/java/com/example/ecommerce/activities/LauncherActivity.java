package com.example.ecommerce.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LauncherActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);

        boolean isFirstTime = sharedPreferences.getBoolean("First Time", true);
        boolean isLoggedIn = auth.getCurrentUser() != null;

        if (isFirstTime) {
            // First time launch, show onboarding
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("First Time", false);
            editor.apply();
            startActivity(new Intent(LauncherActivity.this, RegistrationActivity.class));
        } else if (isLoggedIn) {
            // User is logged in, go to main activity
            startActivity(new Intent(LauncherActivity.this, MainActivity.class));
        } else {
            // User is not logged in, go to registration activity
            startActivity(new Intent(LauncherActivity.this, RegistrationActivity.class));
        }

        finish();
    }
}
