package com.yls.recorder.recorder;

import android.content.Context;
import android.os.Looper;

import androidx.annotation.MainThread;

public class Recoder implements IRecord {

    private static final int IMPL_CONFIG = 1;
    private static final int IMPL_MEDIARECORDER = 1;
    private static final int IMPL_AUDIORECORD = 2;
    private IRecordImpl mRecordImpl;

    public Recoder(Context context) {
        init(context);
    }

    // 参考 https://www.cnblogs.com/renhui/p/11704635.html
    @MainThread
    @Override
    public void setListener(VolumeListener listener) {
        mRecordImpl.setListener(listener);
    }

    @MainThread
    @Override
    public void init(Context context) {
        if (IMPL_CONFIG == IMPL_MEDIARECORDER) {
            mRecordImpl = new MediaRecorderImpl(context, Looper.getMainLooper());
        } else {
            mRecordImpl = new AudioRecordImpl();
        }
    }

    @MainThread
    @Override
    public void start() {
        mRecordImpl.start();
    }

    @MainThread
    @Override
    public void pause() {
        mRecordImpl.pause();
    }

    @MainThread
    @Override
    public void stop() {
        mRecordImpl.stop();
    }
}
