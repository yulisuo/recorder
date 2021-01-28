package com.yls.recorder;

import android.content.Intent;
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

        if (PermissionManager.hasAllPermissions(this)) {
            mRecoder = new Recoder(this);
            mRecoder.setListener(this);
        } else {
            PermissionManager.requestPermissions(this, REQUEST_PERMISSION_CODE);
        }
    }

    private void initView() {
        startOrStop = findViewById(R.id.start_or_stop);
        startOrStop.setOnClickListener(this);
        Button list = findViewById(R.id.list);
        list.setOnClickListener(this);
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
            case R.id.list:
                gotoListActivity();
                break;
            default:
                break;
        }
    }

    private void gotoListActivity() {
        Intent intent = new Intent(this, RecordFileListActivity.class);
        startActivity(intent);
    }

    private void startRecord() {
        isStart = true;
        startOrStop.setText(R.string.stop);
        if (mRecoder != null) {
            mRecoder.start();
        }
    }

    private void stopRecord() {
        isStart = false;
        startOrStop.setText(R.string.start);
        if (mRecoder != null) {
            mRecoder.stop();
        }
    }

    @Override
    public void onChange(int volume) {
        mVolumeText.setText("" + volume);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (PermissionManager.handleResult(permissions, grantResults)) {
                if (mRecoder == null) {
                    mRecoder = new Recoder(this);
                    mRecoder.setListener(this);
                }
            } else {
                Toast.makeText(this, "应用需要获取权限才能正常使用", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}