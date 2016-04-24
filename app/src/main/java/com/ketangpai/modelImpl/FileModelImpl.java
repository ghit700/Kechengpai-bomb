package com.ketangpai.modelImpl;

import android.content.Context;
import android.util.Log;

import com.ketangpai.Callback.AttachmentResultCallback;
import com.ketangpai.fragment.AddNoticeFragment;
import com.ketangpai.model.FileModel;
import com.ketangpai.utils.FileUtils;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by nan on 2016/4/24.
 */
public class FileModelImpl implements FileModel {
    @Override
    public void uploadAttachment(final Context context, final BmobFile bmobFile, final AttachmentResultCallback resultCallback) {
        Log.i(AddNoticeFragment.TAG, "uploadAttachment fileName=" + bmobFile.getFilename());

        bmobFile.upload(context, new UploadFileListener() {
            @Override
            public void onSuccess() {
                
                resultCallback.onSuccess(bmobFile);
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
}
