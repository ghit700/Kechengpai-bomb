package com.ketangpai.modelImpl;

import android.content.Context;
import android.util.Log;

import com.ketangpai.Callback.AttachmentResultCallback;
import com.ketangpai.Callback.ResultCallback;
import com.ketangpai.bean.Notice;
import com.ketangpai.fragment.AddNoticeFragment;
import com.ketangpai.model.NoticeModel;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by nan on 2016/4/22.
 */
public class NoticeModelImpl implements NoticeModel {
    @Override
    public void uploadAttachment(final Context context, File file, final AttachmentResultCallback resultCallback) {
        Log.i(AddNoticeFragment.TAG, "uploadAttachment fileName=" + file.getName());
        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.upload(context, new UploadFileListener() {
            @Override
            public void onSuccess() {
                resultCallback.onSuccess(bmobFile.getFileUrl(context));
            }


            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
                resultCallback.onProgress(value);
            }

            @Override
            public void onFailure(int i, String s) {
                resultCallback.onFailure(s);
            }
        });
    }

    @Override
    public void publishNotice(Context context, Notice notice, SaveListener resultCallback) {
        Log.i(AddNoticeFragment.TAG, "publishNotice c_id=" + notice.getC_id() + " title=" + notice.getTitle() +
                " content=" + notice.getContent() + " paths=" + notice.getPaths());
        notice.save(context, resultCallback);
    }
}
