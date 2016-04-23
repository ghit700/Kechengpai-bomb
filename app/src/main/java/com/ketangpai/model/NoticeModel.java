package com.ketangpai.model;

import android.content.Context;

import com.ketangpai.Callback.AttachmentResultCallback;
import com.ketangpai.Callback.ResultCallback;
import com.ketangpai.bean.Notice;

import java.io.File;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by nan on 2016/4/22.
 */
public interface NoticeModel {
    void uploadAttachment(Context context, File file, AttachmentResultCallback resultCallback);
    void publishNotice(Context context, Notice notice, SaveListener resultCallback);
}
