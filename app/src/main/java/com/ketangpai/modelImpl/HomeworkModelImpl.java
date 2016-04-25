package com.ketangpai.modelImpl;

import android.content.Context;
import android.util.Log;

import com.ketangpai.Callback.ResultsCallback;
import com.ketangpai.bean.Notice;
import com.ketangpai.bean.Teacher_Homework;
import com.ketangpai.fragment.AddHomeworkFragment;
import com.ketangpai.fragment.AddNoticeFragment;
import com.ketangpai.fragment.CourseTabFragment;
import com.ketangpai.model.HomeworkModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by nan on 2016/4/24.
 */
public class HomeworkModelImpl implements HomeworkModel {
    @Override
    public void publishHomework(Context context, Teacher_Homework homework, SaveListener resultCallback) {
        Log.i(AddHomeworkFragment.TAG, "publishHomework c_id=" + homework.getC_id() + " title=" + homework.getTitle() +
                " content=" + homework.getContent());
        homework.save(context, resultCallback);
    }

    @Override
    public void getHomeworkList(Context context, int c_id, final ResultsCallback resultsCallback) {
        Log.i(AddHomeworkFragment.TAG, "getHomeworkList c_id=" + c_id);
        String sql = "select * from Teacher_Homework where c_id=?";
        BmobQuery<Teacher_Homework> bmobQuery = new BmobQuery<Teacher_Homework>();
        bmobQuery.doSQLQuery(context, sql, new SQLQueryListener<Teacher_Homework>() {
            @Override
            public void done(BmobQueryResult<Teacher_Homework> bmobQueryResult, BmobException e) {
                List list = bmobQueryResult.getResults();
                if (null != list) {
                    resultsCallback.onSuccess(list);
                } else {
                    Log.i(AddHomeworkFragment.TAG, "getHomeworkList null");

                }
            }
        }, c_id);
    }
}
