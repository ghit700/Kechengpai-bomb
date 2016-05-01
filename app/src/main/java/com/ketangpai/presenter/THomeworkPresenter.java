package com.ketangpai.presenter;

import android.content.Context;
import android.util.Log;

import com.ketangpai.base.BasePresenter;
import com.ketangpai.bean.Student_Homework;
import com.ketangpai.callback.ResultsCallback;
import com.ketangpai.fragment.THomeworkFragment;
import com.ketangpai.model.HomeworkModel;
import com.ketangpai.modelImpl.HomeworkModelImpl;
import com.ketangpai.viewInterface.THomeworkViewInterface;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by nan on 2016/5/1.
 */
public class THomeworkPresenter extends BasePresenter<THomeworkViewInterface> {
    THomeworkViewInterface mTHomeworkViewInterface;
    HomeworkModel mHomeworkModel = new HomeworkModelImpl();


    public THomeworkPresenter() {
        mHomeworkModel = new HomeworkModelImpl();
    }

    public void getStudentHomeworkList(Context context, int h_id) {
        if (isViewAttached()) {
            mTHomeworkViewInterface = getView();
            mHomeworkModel.getStudentHomeworkList(context, h_id, new ResultsCallback() {
                @Override
                public void onSuccess(List list) {
                    mTHomeworkViewInterface.getStudentHomeworkListOnComplete(list);
                }

                @Override
                public void onFailure(BmobException e) {

                }
            });
        }
    }
}
