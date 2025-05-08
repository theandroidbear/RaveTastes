package com.ravemaster.recipeapp.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.ravemaster.recipeapp.databinding.ActivityPagerTestingBinding;

public class PagerActivityTesting extends AppCompatActivity {

    ActivityPagerTestingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPagerTestingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}