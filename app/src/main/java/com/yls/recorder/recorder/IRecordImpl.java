package com.yls.recorder.recorder;

public interface IRecordImpl {
    void init();
    void start();
    void pause();
    void stop();
    void setListener(VolumeListener listener);
}
