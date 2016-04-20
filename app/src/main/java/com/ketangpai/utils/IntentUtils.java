package com.ketangpai.utils;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;

import com.ketangpai.base.BaseFragment;
import com.ketangpai.fragment.AddNoticeFragment;
import com.ketangpai.fragment.CourseTabFragment;

/**
 * Created by Administrator on 2016/4/15.
 */
public class IntentUtils {

    public static final int OPEN_DOCUMENT_REQUEST = 1;
    public static final int OPEN_IMGAE = 2;

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
        intent.setType("image/video/audio/*");
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
        fragment.startActivityForResult(intent, OPEN_DOCUMENT_REQUEST);
    }
}
