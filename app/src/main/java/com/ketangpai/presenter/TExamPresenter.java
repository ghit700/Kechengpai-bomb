package com.ketangpai.presenter;

import android.content.Context;

import com.ketangpai.base.BasePresenter;
import com.ketangpai.callback.ResultsCallback;
import com.ketangpai.model.ExamModel;
import com.ketangpai.model.HomeworkModel;
import com.ketangpai.modelImpl.ExamModelImpl;
import com.ketangpai.modelImpl.HomeworkModelImpl;
import com.ketangpai.viewInterface.TExamViewInterface;
import com.ketangpai.viewInterface.THomeworkViewInterface;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by nan on 2016/5/8.
 */
public class TExamPresenter extends BasePresenter<TExamViewInterface> {

    TExamViewInterface mTExamViewInterface;
    ExamModel mExamModel;


    public TExamPresenter() {
        mExamModel = new ExamModelImpl();
    }

    public void getStudentReplykList(Context context, int t_id) {
        if (isViewAttached()) {
            mTExamViewInterface = getView();
            mExamModel.getStudentExamList(context, t_id, new ResultsCallback() {
                @Override
                public void onSuccess(List list) {
                    mTExamViewInterface.getStudentReplyListOnComplete(list);
                }

                @Override
                public void onFailure(BmobException e) {

                }
            });


        }
    }
}
