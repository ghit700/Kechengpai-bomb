package com.ketangpai.presenter;

import android.content.Context;
import android.util.Log;

import com.ketangpai.Callback.AttachmentResultCallback;
import com.ketangpai.base.BasePresenter;
import com.ketangpai.bean.Data;
import com.ketangpai.bean.Notice;
import com.ketangpai.bean.Teacher_Homework;
import com.ketangpai.fragment.AddHomeworkFragment;
import com.ketangpai.fragment.AddNoticeFragment;
import com.ketangpai.model.FileModel;
import com.ketangpai.model.HomeworkModel;
import com.ketangpai.modelImpl.FileModelImpl;
import com.ketangpai.modelImpl.HomeworkModelImpl;
import com.ketangpai.modelImpl.NoticeModelImpl;
import com.ketangpai.utils.FileUtils;
import com.ketangpai.viewInterface.AddHomeworkViewInterface;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by nan on 2016/4/24.
 */
public class AddHomeworkPresenter extends BasePresenter<AddHomeworkViewInterface> {
    private AddHomeworkViewInterface mAddHomeworkViewInterface;
    private FileModel fileModel;
    private HomeworkModel homeworkModel;

    public AddHomeworkPresenter() {
        homeworkModel = new HomeworkModelImpl();
        fileModel = new FileModelImpl();
    }

    public void uploadAttachment(final Context context, final BmobFile file) {
        if (isViewAttached()) {
            mAddHomeworkViewInterface = getView();
            fileModel.uploadAttachment(context, file, new AttachmentResultCallback() {
                @Override
                public void onSuccess(BmobFile bmobFile) {
                    Data data = new Data();
                    data.setUrl(bmobFile.getFileUrl(context));
                    data.setSize(FileUtils.getFileSize(file.getLocalFile().length()));
                    data.setName(file.getFilename());
                    mAddHomeworkViewInterface.uploadAttachmentOnComplete(data);
                }

                @Override
                public void onProgress(Integer value) {
                    mAddHomeworkViewInterface.onProgress(value);
                }

                @Override
                public void onFailure(String e) {
                    Log.i(AddNoticeFragment.TAG, e);
                }
            });
        } else {
            Log.i(AddHomeworkFragment.TAG, "addAttachment 没有连接到view");
        }
    }

    public void publishHomework(Context context, final Teacher_Homework homework) {
        if (isViewAttached()) {
            mAddHomeworkViewInterface = getView();
            homeworkModel.publishHomework(context, homework, new SaveListener() {
                @Override
                public void onSuccess() {
                    mAddHomeworkViewInterface.addHomeWorkOnComplete(homework);
                }

                @Override
                public void onFailure(int i, String s) {
                    Log.i(AddHomeworkFragment.TAG, s);
                }
            });
        } else {
            Log.i(AddHomeworkFragment.TAG, "publishNotice 没有连接到view");
        }
    }

}
