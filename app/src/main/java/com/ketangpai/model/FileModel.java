package com.ketangpai.model;

import android.content.Context;

import com.ketangpai.Callback.AttachmentResultCallback;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by nan on 2016/4/24.
 */
public interface FileModel {
    void uploadAttachment(Context context, BmobFile file, AttachmentResultCallback resultCallback);

}
