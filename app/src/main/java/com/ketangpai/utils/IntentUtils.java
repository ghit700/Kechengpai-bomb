package com.ketangpai.utils;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;

import com.ketangpai.base.BaseFragment;
import com.ketangpai.constant.Constant;
import com.ketangpai.fragment.AddNoticeFragment;
import com.ketangpai.fragment.CourseTabFragment;

import java.io.File;

/**
 * Created by Administrator on 2016/4/15.
 */
public class IntentUtils {

    public static final int OPEN_DOCUMENT_REQUEST = 1;
    public static final int OPEN_IMGAE = 2;
    public static final int CAMERA_REQUEST = 101;


    /**
     * 读取设备文档和视频,返回文件的Uri
     *
     * @param fragment
     */
    public static void openDocument(BaseFragment fragment) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //仅返回可以打开流的文件
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/*");
        fragment.startActivityForResult(intent, OPEN_DOCUMENT_REQUEST);
    }

    /**
     * 打开图片文件
     *
     * @param fragment
     */
    public static void openImageFile(BaseFragment fragment) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //仅返回可以打开流的文件
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        fragment.startActivityForResult(intent, OPEN_IMGAE);
    }


    /**
     * 打开相机,返回文件路径
     *
     * @param fragment
     * @return
     */
    public static String openCamera(BaseFragment fragment) {
        String File_Path = Constant.LOGO_FOLDER;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(File_Path))); //文件的名字
        fragment.startActivityForResult(intent, CAMERA_REQUEST);
        return File_Path;
    }

    public static void openFile(BaseFragment fragment, Uri uri) {
        Intent intent = new Intent();
        intent.setType("application/*");
        intent.setData(uri);
        fragment.startActivity(intent);
    }
}
