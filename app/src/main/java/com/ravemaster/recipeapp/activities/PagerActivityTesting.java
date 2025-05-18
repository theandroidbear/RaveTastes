package com.ravemaster.recipeapp.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.runtime.Composer;

import com.ravemaster.recipeapp.composables.SampleComposableKt;
import com.ravemaster.recipeapp.databinding.ActivityPagerTestingBinding;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

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