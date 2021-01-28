package com.yls.recorder.recorder;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.yls.recorder.Utils;

import java.io.File;
import java.io.IOException;

public class MediaRecorderImpl implements IRecordImpl {

    private static final int MAX_LENGTH = 30_000;
    private static final String TAG = "MediaRecorderImpl";
    private MediaRecorder mMediaRecorder;
    private Handler mHandler;
    private String mPath;
    private double db;
    private VolumeListener mVolumeListener;

    public MediaRecorderImpl(Context context, Looper looper) {
        Log.i(TAG, "new MediaRecorderImpl");
        mHandler = new Handler(looper);
        mPath = Utils.getPath(context);
        init();
    }

    @Override
    public void init() {
        Log.i(TAG, "init");
        mMediaRecorder = new MediaRecorder();

        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
        // 设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        /*
         * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
         * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
         */
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

//        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "111.amr";
        Log.i(TAG, "init, path:" + mPath);
        mMediaRecorder.setOutputFile(mPath);
        mMediaRecorder.setMaxDuration(MAX_LENGTH);
    }

    @Override
    public void start() {
        if (mMediaRecorder != null) {
            try {
                mMediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "start");
            mMediaRecorder.start();
            updateVolume();
        } else {
            Log.i(TAG, "start, mMediaRecorder is null.");
        }
    }

    @Override
    public void pause() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.i(TAG, "pause");
            mMediaRecorder.pause();
        }
    }

    @Override
    public void stop() {
        if (mMediaRecorder == null) {
            return;
        }
        Log.i(TAG, "stop");
        mMediaRecorder.stop();
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }

    @Override
    public void setListener(VolumeListener listener) {
        mVolumeListener = listener;
    }

    private void updateVolume() {
        if (mMediaRecorder != null) {
            Log.i(TAG, "updateVolume");
            double ratio = (double) mMediaRecorder.getMaxAmplitude() / 1;   // 参考振幅为 1
            db = 0;// 分贝
            if (ratio > 1) {
                db = 20 * Math.log10(ratio);
            }
            if (mVolumeListener != null) {
                mVolumeListener.onChange((int) db);
            }
            Log.d(TAG, "计算分贝值 = " + db + "dB");
            mHandler.postDelayed(this::updateVolume, 100); // 间隔取样时间为100秒
        } else {
            Log.i(TAG, "updateVolume, mMediaRecorder is null.");
        }
    }
}
