package com.yls.recorder;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

// https://developer.android.com/training/permissions/requesting#java
public class PermissionManager {

    private static final String TAG = "PermissionManager";
    private static final String[] PERMISSIONS = new String[] {Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public static void requestPermissions(AppCompatActivity context, int code) {
        ArrayList<String> needRequestPermissions = new ArrayList<>();
        for (String p : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, p) != PackageManager.PERMISSION_GRANTED) {
                needRequestPermissions.add(p);
            }
        }
        if (!needRequestPermissions.isEmpty()) {
            // 申请权限
            ActivityCompat.requestPermissions(context, needRequestPermissions.toArray(new String[0]), code);
        }
    }

    public static boolean hasAllPermissions(Context context) {
        for (String p : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(context, p) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean handleResult(String[] permissions, int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "permission:" + permissions[i] + " not granted.");
                return false;
            }
        }
        return true;
    }
}
