package com.ketangpai.presenter;

import android.content.Context;
import android.util.Log;

import com.ketangpai.Callback.AttachmentResultCallback;
import com.ketangpai.base.BasePresenter;
import com.ketangpai.bean.Notice;
import com.ketangpai.fragment.AddNoticeFragment;
import com.ketangpai.model.FileModel;
import com.ketangpai.model.NoticeModel;
import com.ketangpai.modelImpl.FileModelImpl;
import com.ketangpai.modelImpl.NoticeModelImpl;
import com.ketangpai.viewInterface.AddNoticeViewInterface;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by nan on 2016/4/22.
 */
public class AddNoticePresenter extends BasePresenter<AddNoticeViewInterface> {

    NoticeModel noticeModel;
    FileModel fileModel;
    AddNoticeViewInterface addNoticeViewInterface;

    public AddNoticePresenter() {
        noticeModel = new NoticeModelImpl();
        fileModel = new FileModelImpl();
    }

    public void uploadAttachment(Context context, BmobFile file) {
        if (isViewAttached()) {
            addNoticeViewInterface = getView();
            fileModel.uploadAttachment(context, file, new AttachmentResultCallback() {
                @Override
                public void onSuccess(BmobFile bmobFile) {
                    addNoticeViewInterface.uploadAttachmentOnComplete(bmobFile);
                }

                @Override
                public void onProgress(Integer value) {
                    addNoticeViewInterface.onProgress(value);
                }

                @Override
                public void onFailure(String e) {
                    Log.i(AddNoticeFragment.TAG, e);
                }
            });
        } else {
            Log.i(AddNoticeFragment.TAG, "addAttachment 没有连接到view");
        }
    }

    public void publishNotice(Context context, final Notice notice) {
        if (isViewAttached()) {
            addNoticeViewInterface = getView();
            noticeModel.publishNotice(context, notice, new SaveListener() {
                @Override
                public void onSuccess() {
                    addNoticeViewInterface.publishNoticeOnComplete(notice);
                }

                @Override
                public void onFailure(int i, String s) {
                    Log.i(AddNoticeFragment.TAG, s);
                }
            });
        } else {
            Log.i(AddNoticeFragment.TAG, "publishNotice 没有连接到view");
        }
    }

    ;

}
