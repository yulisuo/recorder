package com.yls.recorder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.yls.recorder.recorder.Recoder;
import com.yls.recorder.recorder.VolumeListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, VolumeListener {

    private static final int REQUEST_PERMISSION_CODE = 101;
    private Recoder mRecoder;
    private Button startOrStop;
    private TextView mVolumeText;
    private boolean isStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mRecoder = new Recoder(this);
        mRecoder.setListener(this);

        if (!PermissionManager.hasAllPermissions(this)) {
            PermissionManager.requestPermissions(this, REQUEST_PERMISSION_CODE);
        }
    }

    private void initView() {
        startOrStop = findViewById(R.id.start_or_stop);
        startOrStop.setOnClickListener(this);
        mVolumeText = findViewById(R.id.volume);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_or_stop:
                if (isStart) {
                    stopRecord();
                } else {
                    startRecord();
                }
                break;
            default:
                break;
        }
    }

    private void startRecord() {
        isStart = true;
        startOrStop.setText(R.string.stop);
        mRecoder.start();
    }

    private void stopRecord() {
        isStart = false;
        startOrStop.setText(R.string.start);
        mRecoder.stop();
    }

    @Override
    public void onChange(int volume) {
        mVolumeText.setText("" + volume);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (!PermissionManager.handleResult(permissions, grantResults)) {
                Toast.makeText(this, "应用需要获取权限才能正常使用", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}