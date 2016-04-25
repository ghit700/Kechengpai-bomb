package com.ketangpai.presenter;

import android.content.Context;
import android.util.Log;

import com.ketangpai.Callback.ResultCallback;
import com.ketangpai.Callback.ResultsCallback;
import com.ketangpai.Callback.UploadFileResultCallback;
import com.ketangpai.base.BasePresenter;
import com.ketangpai.bean.Course;
import com.ketangpai.bean.Data;
import com.ketangpai.bean.Homework;
import com.ketangpai.fragment.CourseTabFragment;
import com.ketangpai.model.DataModel;
import com.ketangpai.model.HomeworkModel;
import com.ketangpai.model.NoticeModel;
import com.ketangpai.modelImpl.DataModelImpl;
import com.ketangpai.modelImpl.HomeworkModelImpl;
import com.ketangpai.modelImpl.NoticeModelImpl;
import com.ketangpai.viewInterface.CourseTabViewInterface;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by nan on 2016/4/24.
 */
public class CourseTabPresenter extends BasePresenter<CourseTabViewInterface> {

    CourseTabViewInterface courseTabViewInterface;
    NoticeModel noticeModel;
    DataModel dataModel;
    HomeworkModel homeworkModel;

    public CourseTabPresenter() {
        homeworkModel = new HomeworkModelImpl();
        dataModel = new DataModelImpl();
        noticeModel = new NoticeModelImpl();
    }

    public void getNoticeList(Context context, int c_id) {
        if (isViewAttached()) {
            courseTabViewInterface = getView();
            noticeModel.getNoticeList(context, c_id, new ResultsCallback() {
                @Override
                public void onSuccess(List list) {
                    courseTabViewInterface.getNoticeListOnComplete(list);
                }

                @Override
                public void onFailure(BmobException e) {

                }
            });
        }
    }

    public void uploadData(Context context, Data data) {
        if (isViewAttached()) {
            courseTabViewInterface = getView();
            dataModel.uploadData(context, data, new UploadFileResultCallback() {
                @Override
                public void onSuccess(String url) {
                    courseTabViewInterface.uploadDataOnComplete(url);
                }

                @Override
                public void onProgress(int value) {
                    courseTabViewInterface.uploadOnProgress(value);
                }

                @Override
                public void onFailure(String e) {
                    Log.i(CourseTabFragment.TAG, "uploadData " + e);
                }
            });
        }
    }

    public void getDataList(Context context, int c_id) {
        if (isViewAttached()) {
            courseTabViewInterface = getView();
            dataModel.getDataList(context, c_id, new ResultsCallback() {
                @Override
                public void onSuccess(List list) {
                    courseTabViewInterface.getDataListOnComplete(list);
                }

                @Override
                public void onFailure(BmobException e) {
                    Log.i(CourseTabFragment.TAG, "getDataList " + e);
                }
            });
        }
    }

    public void getHomeworkList(Context context, int c_id) {
        if (isViewAttached()) {
            courseTabViewInterface = getView();
            homeworkModel.getHomeworkList(context, c_id, new ResultsCallback() {
                @Override
                public void onSuccess(List list) {
                    courseTabViewInterface.getHomeworkListOnComplete(list);
                }

                @Override
                public void onFailure(BmobException e) {
                    Log.i(CourseTabFragment.TAG, "getHomeworkList " + e);
                }
            });
        }
    }


}
