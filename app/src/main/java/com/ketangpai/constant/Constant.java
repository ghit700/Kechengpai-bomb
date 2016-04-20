package com.ketangpai.constant;

import android.os.Environment;

import java.io.File;

/**
 * Created by nan on 2016/4/9.
 */
public class Constant {

    public static final String LOG_TAG = "===wu";

    public final static String MAIN_Folder = "ketangpai";
    public final static String DATA_Folder = "data";
    public static final String ALBUM_PATH = Environment.getExternalStorageState() + File.separator + MAIN_Folder + File.separator;
}
