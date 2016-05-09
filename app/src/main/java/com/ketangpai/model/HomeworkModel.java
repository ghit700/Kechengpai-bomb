package com.ketangpai.model;

import android.content.Context;

import com.ketangpai.bean.Student_Course;
import com.ketangpai.bean.Student_Homework;
import com.ketangpai.callback.ResultCallback;
import com.ketangpai.callback.ResultsCallback;
import com.ketangpai.bean.Teacher_Homework;

import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by nan on 2016/4/24.
 */
public interface HomeworkModel {


    void getHomeworkList(Context context, int c_id, ResultsCallback resultsCallback);


    void getHomeworkListToStudent(Context context,int c_id,long add_time,ResultsCallback resultsCallback);


    void getStudentHomewokr(Context context, Teacher_Homework homework,String account ,ResultCallback resultCallback);

    void publishStudentHomework(Context context,Student_Homework student_homework);
}
