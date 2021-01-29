package com.yls.recorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.yls.Player;

public class PlayActivity extends AppCompatActivity {

    Player mPlayer;
    public static final String EXTRA_KEY_FILE = "file";
    private boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        mPlayer = new Player(getIntent().getParcelableExtra(EXTRA_KEY_FILE));

        initViews();
    }

    private void initViews() {
        Button startOrStop = findViewById(R.id.start_or_stop);
        startOrStop.setOnClickListener(v -> {
            if (isPlaying) {
                isPlaying = false;
                startOrStop.setText(R.string.start);
                mPlayer.stop();
            } else {
                isPlaying = true;
                startOrStop.setText(R.string.stop);
                mPlayer.start();
            }
        });
    }
}