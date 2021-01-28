package com.yls.recorder;

import android.content.Context;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private static String EX_NAME = ".amr";

    public static String getPath(Context context) {
        return getRecorderFileFoler(context) + File.separator + getFileNameByTime() + EX_NAME;
    }

    private static String getFileNameByTime() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        return sf.format(new Date());
    }

    public static File getRecorderFileFoler(Context context) {
        return context.getFilesDir();
    }
}
