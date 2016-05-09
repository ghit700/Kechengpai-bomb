package com.ketangpai.modelImpl;

import android.content.Context;
import android.util.Log;

import com.ketangpai.bean.Student_Course;
import com.ketangpai.bean.Student_Homework;
import com.ketangpai.callback.ResultCallback;
import com.ketangpai.callback.ResultsCallback;
import com.ketangpai.bean.Teacher_Homework;
import com.ketangpai.model.HomeworkModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by nan on 2016/4/24.
 */
public class HomeworkModelImpl implements HomeworkModel {


    @Override
    public void getHomeworkList(Context context, int c_id, final ResultsCallback resultsCallback) {
        String sql = "select * from Teacher_Homework where c_id=?";
        BmobQuery<Teacher_Homework> bmobQuery = new BmobQuery<Teacher_Homework>();
        bmobQuery.doSQLQuery(context, sql, new SQLQueryListener<Teacher_Homework>() {
            @Override
            public void done(BmobQueryResult<Teacher_Homework> bmobQueryResult, BmobException e) {
                List list = bmobQueryResult.getResults();
                if (null != list) {
                    resultsCallback.onSuccess(list);
                } else {

                }
            }
        }, c_id);
    }


    @Override
    public void getHomeworkListToStudent(Context context, int c_id, long add_time, final ResultsCallback resultsCallback) {
        String sql = "select * from Teacher_Homework where c_id =? and p_time>?";

        BmobQuery<Teacher_Homework> query = new BmobQuery();

        query.doSQLQuery(context, sql, new SQLQueryListener<Teacher_Homework>() {
            @Override
            public void done(BmobQueryResult<Teacher_Homework> bmobQueryResult, BmobException e) {
                List<Teacher_Homework> homeworks = bmobQueryResult.getResults();
                if (null != homeworks && homeworks.size() > 0) {
                    resultsCallback.onSuccess(homeworks);
                } else {
                    resultsCallback.onFailure(e);
                }


            }
        }, c_id, add_time);

    }


    /**
     * 获取学生作业信息
     *
     * @param context
     * @param homework
     * @param resultCallback
     */
    @Override
    public void getStudentHomewokr(Context context, Teacher_Homework homework, String account, final ResultCallback resultCallback) {
        String sql = "select * from Student_Homework where h_id=? and account=?";
        BmobQuery<Student_Homework> query = new BmobQuery<>();
        query.doSQLQuery(context, sql, new SQLQueryListener<Student_Homework>() {
            @Override
            public void done(BmobQueryResult<Student_Homework> bmobQueryResult, BmobException e) {
                List<Student_Homework> student_homeworks = bmobQueryResult.getResults();
                if (null != student_homeworks && student_homeworks.size() > 0) {
                    resultCallback.onSuccess(student_homeworks.get(0));
                }
            }
        }, homework.getH_id(), account);
    }

    /**
     * 学生上传作业
     *
     * @param context
     * @param student_homework
     */
    @Override
    public void publishStudentHomework(final Context context, final Student_Homework student_homework) {
        student_homework.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                BmobQuery<Teacher_Homework> query = new BmobQuery<Teacher_Homework>();
                query.addWhereEqualTo("h_id", student_homework.getH_id());
                query.findObjects(context, new FindListener<Teacher_Homework>() {
                    @Override
                    public void onSuccess(List<Teacher_Homework> list) {
                        if (null != list && list.size() > 0) {
                            Teacher_Homework teacher_homework = list.get(0);
                            int no_hander = teacher_homework.getNo_hander_count();
                            no_hander--;
                            teacher_homework.setNo_hander_count(no_hander);
                            int no_check_count = teacher_homework.getNo_check_count();
                            no_check_count++;
                            teacher_homework.setNo_check_count(no_check_count);
                            teacher_homework.update(context);


                        }
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }


}
