package com.ravemaster.recipeapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.ravemaster.recipeapp.R;

public class VideoActivity extends AppCompatActivity {

    ExoPlayer exoPlayer;
    String url = "";
    PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video);

        initViews();

        Intent intent = getIntent();
        url = intent.getStringExtra("videoUrl");

        exoPlayer = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(exoPlayer);
        MediaItem mediaItem = MediaItem.fromUri(url);
        exoPlayer.setMediaItem(mediaItem);
        exoPlayer.prepare();
        exoPlayer.setPlayWhenReady(true);

    }

    @Override
    protected void onStop() {
        super.onStop();
        exoPlayer.setPlayWhenReady(false);
        exoPlayer.release();
        exoPlayer = null;
    }

    private void initViews() {
        playerView = findViewById(R.id.playerView);
    }
}