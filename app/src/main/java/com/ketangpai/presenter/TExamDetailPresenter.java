package com.ketangpai.presenter;

import android.content.Context;
import android.util.Log;

import com.ketangpai.base.BasePresenter;
import com.ketangpai.bean.Student_Homework;
import com.ketangpai.bean.Student_Reply;
import com.ketangpai.model.ExamModel;
import com.ketangpai.model.HomeworkModel;
import com.ketangpai.modelImpl.ExamModelImpl;
import com.ketangpai.modelImpl.HomeworkModelImpl;
import com.ketangpai.viewInterface.TExamDetailViewInterface;
import com.ketangpai.viewInterface.TExamViewInterface;
import com.ketangpai.viewInterface.THomeworkDetailViewInterface;

import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by nan on 2016/5/1.
 */
public class TExamDetailPresenter extends BasePresenter<TExamDetailViewInterface> {
    private TExamDetailViewInterface mTExamDetailViewInterface;
    private ExamModel mExamModel;

    public TExamDetailPresenter() {
        mExamModel = new ExamModelImpl();
    }

    public void correctExam(Context context, Student_Reply student_reply) {
        if (isViewAttached()) {
            mTExamDetailViewInterface = getView();

            mExamModel.correctExam(context, student_reply, new UpdateListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(int i, String s) {

                }
            });


        }
    }

    ;
}
