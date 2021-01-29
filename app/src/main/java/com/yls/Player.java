package com.yls;

import android.media.MediaPlayer;

import java.io.File;
import java.io.IOException;

public class Player {

    private File mFile;
    private MediaPlayer mMediaPlayer;

    public Player(File file) {
        mFile = file;
        init();
    }

    private void init() {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(mFile.getPath());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
        }
    }

    public void stop() {
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }
}
