package com.ketangpai.model;

import android.content.Context;

import com.ketangpai.Callback.ResultsCallback;
import com.ketangpai.Callback.ResultCallback;
import com.ketangpai.bean.Teacher_Course;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/4/19.
 */
public interface CourseModel {
    void queryCourseList(Context context, String account, int type, ResultsCallback resultsCallback);

    void createCourse(Context context, Teacher_Course course, ResultCallback resultCallback);

    void addCourse(Context context, String code, String account, ResultCallback resultCallback);
}
