package com.ketangpai.modelImpl;

import android.content.Context;

import com.ketangpai.bean.Student_Reply;
import com.ketangpai.bean.Test;
import com.ketangpai.callback.ResultCallback;
import com.ketangpai.callback.ResultsCallback;
import com.ketangpai.model.ExamModel;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by nan on 2016/5/5.
 */
public class ExamModelImpl implements ExamModel {
    @Override
    public void publishExam(Context context, Test test, SaveListener resultCallback) {
        
    }

    @Override
    public void createStudentExamList(Context context, Test test) {

    }

    @Override
    public void getExamList(Context context, int c_id, ResultsCallback resultsCallback) {

    }

    @Override
    public void getStudentExamList(Context context, int t_id, ResultsCallback resultsCallback) {

    }

    @Override
    public void getExamListToStudent(Context context, int c_id, long add_time, ResultsCallback resultsCallback) {

    }

    @Override
    public void correctExam(Context context, Student_Reply student_reply, UpdateListener resultCallback) {

    }

    @Override
    public void getStudentExam(Context context, Test test, String account, ResultCallback resultCallback) {

    }

    @Override
    public void publishStudentExam(Context context, Student_Reply student_reply) {

    }
}
