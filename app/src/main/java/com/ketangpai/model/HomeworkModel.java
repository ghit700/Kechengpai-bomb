package com.ketangpai.model;

import android.content.Context;

import com.ketangpai.Callback.ResultsCallback;
import com.ketangpai.bean.Notice;
import com.ketangpai.bean.Teacher_Course;
import com.ketangpai.bean.Teacher_Homework;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by nan on 2016/4/24.
 */
public interface HomeworkModel {
    void publishHomework(Context context, Teacher_Homework homework, SaveListener resultCallback);

    void getHomeworkList(Context context, int c_id, ResultsCallback resultsCallback);
}
