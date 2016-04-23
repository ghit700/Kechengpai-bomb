package com.ketangpai.constant;

import android.os.Environment;

import java.io.File;

/**
 * Created by nan on 2016/4/9.
 */
public class Constant {


    public final static String MAIN_Folder = "ketangpai" + File.separator;
    public final static String DATA_Folder = "data" + File.separator;
    public final static String PHOTO_Folder = "photo" + File.separator;
    public static final String ALBUM_PATH = Environment.getExternalStorageDirectory() + File.separator + MAIN_Folder + File.separator;


}
