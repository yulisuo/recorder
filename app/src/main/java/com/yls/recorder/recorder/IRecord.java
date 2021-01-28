package com.yls.recorder.recorder;

import android.content.Context;

public interface IRecord {
    void init(Context context);
    void start();
    void pause();
    void stop();
    void setListener(VolumeListener listener);
}
