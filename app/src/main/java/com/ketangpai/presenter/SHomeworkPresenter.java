package com.ketangpai.presenter;

import android.content.Context;
import android.util.Log;

import com.ketangpai.base.BasePresenter;
import com.ketangpai.bean.Data;
import com.ketangpai.bean.Student_Homework;
import com.ketangpai.bean.Teacher_Homework;
import com.ketangpai.callback.AttachmentResultCallback;
import com.ketangpai.callback.ResultCallback;
import com.ketangpai.fragment.AddNoticeFragment;
import com.ketangpai.model.FileModel;
import com.ketangpai.model.HomeworkModel;
import com.ketangpai.model.NotificationModel;
import com.ketangpai.modelImpl.FileModelImpl;
import com.ketangpai.modelImpl.HomeworkModelImpl;
import com.ketangpai.modelImpl.NotificationModelImpl;
import com.ketangpai.utils.FileUtils;
import com.ketangpai.viewInterface.SHomeworkViewInterface;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by nan on 2016/5/1.
 */
public class SHomeworkPresenter extends BasePresenter<SHomeworkViewInterface> {
    SHomeworkViewInterface mSHomeworkViewInterface;
    FileModel fileModel;
    HomeworkModel homeworkModel;
    NotificationModel notificationModel;

    public SHomeworkPresenter() {
        fileModel = new FileModelImpl();
        homeworkModel = new HomeworkModelImpl();
        notificationModel = new NotificationModelImpl();
    }

    /**
     * 上传作业
     *
     * @param context
     * @param file
     */
    public void uploadHomework(final Context context, final BmobFile file) {
        if (isViewAttached()) {
            mSHomeworkViewInterface = getView();
            fileModel.uploadAttachment(context, file, new AttachmentResultCallback() {
                @Override
                public void onSuccess(BmobFile bmobFile) {
                    Data data = new Data();
                    data.setUrl(bmobFile.getFileUrl(context));
                    data.setSize(FileUtils.getFileSize(file.getLocalFile().length()));
                    data.setName(file.getFilename());
                    mSHomeworkViewInterface.uploadAttachOnComplete(data);
                }

                @Override
                public void onProgress(Integer value) {
                    mSHomeworkViewInterface.onProgress(value);
                }

                @Override
                public void onFailure(String e) {
                    Log.i("===SHomework", e);
                }
            });
        }
    }

    /**
     * 获取学生作业信息
     *
     * @param context
     * @param homework
     * @param account
     */
    public void getStudentHomework(Context context, Teacher_Homework homework, String account) {
        if (isViewAttached()) {
            mSHomeworkViewInterface = getView();
            homeworkModel.getStudentHomewokr(context, homework, account, new ResultCallback() {
                @Override
                public void onSuccess(Object object) {
                    mSHomeworkViewInterface.getStudentHomeworkOnComplete((Student_Homework) object);
                }

                @Override
                public void onFailure(String e) {

                }
            });
        }
    }

    public void publishStudentHomework(Context context, Student_Homework homework) {
        if (isViewAttached()) {
            mSHomeworkViewInterface = getView();
            homeworkModel.publishStudentHomework(context, homework);

        }
    }

}
