package com.ketangpai.modelImpl;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.ketangpai.Callback.ResultCallback;
import com.ketangpai.Callback.ResultsCallback;
import com.ketangpai.bean.Course;
import com.ketangpai.bean.Student_Course;
import com.ketangpai.bean.Teacher_Course;
import com.ketangpai.fragment.MainCourseFragment;
import com.ketangpai.model.CourseModel;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/4/19.
 */
public class CourseModelImpl implements CourseModel {
    HashMap<String, String> params;


    @Override
    public void queryCourseList(Context context, String account, int type, final ResultsCallback resultsCallback) {
        String sql = null;
        if (type == 0) {
            sql = "select * from Teacher_Course where account=?";
            Log.i(MainCourseFragment.TAG, "queryCourseList account=" + account + " type=" + type + " sql=" + sql);
            BmobQuery<Teacher_Course> query = new BmobQuery<Teacher_Course>();
            query.doSQLQuery(context, sql, new SQLQueryListener<Teacher_Course>() {
                @Override
                public void done(BmobQueryResult<Teacher_Course> bmobQueryResult, BmobException e) {
                    List<Teacher_Course> list = bmobQueryResult.getResults();
                    if (null != list) {
                        resultsCallback.onSuccess(list);
                    } else {
                        resultsCallback.onFailure(e);
                    }
                }
            }, account);
        } else {
            sql = "select * from Student_Course where account=?";
            Log.i(MainCourseFragment.TAG, "queryCourseList account=" + account + " type=" + type + " sql=" + sql);
            BmobQuery<Student_Course> query = new BmobQuery<Student_Course>();
            query.doSQLQuery(context, sql, new SQLQueryListener<Student_Course>() {
                @Override
                public void done(BmobQueryResult<Student_Course> bmobQueryResult, BmobException e) {
                    List<Student_Course> list = bmobQueryResult.getResults();
                    if (null != list) {
                        resultsCallback.onSuccess(list);
                    } else {
                        resultsCallback.onFailure(e);
                    }
                }
            }, account);
        }
    }

    @Override
    public void createCourse(final Context context, final Teacher_Course course, final ResultCallback resultCallback) {
        Log.i(MainCourseFragment.TAG, "createCourse account=" + course.getAccount() + " id=" + course.getObjectId());
        course.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                BmobQuery<Teacher_Course> query = new BmobQuery<Teacher_Course>();
                query.getObject(context, course.getObjectId(), new GetListener<Teacher_Course>() {
                    @Override
                    public void onSuccess(Teacher_Course o) {
                        resultCallback.onSuccess(o);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        resultCallback.onFailure(s);
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                resultCallback.onFailure(s);
            }
        });
    }

    @Override
    public void addCourse(final Context context, final String code, final String account, final ResultCallback resultCallback) {
        Log.i(MainCourseFragment.TAG, "addCourse code=" + code);
        String sql = "select * from Teacher_Course where code=?";
        BmobQuery<Teacher_Course> query = new BmobQuery<Teacher_Course>();
        query.doSQLQuery(context, sql, new SQLQueryListener<Teacher_Course>() {

            @Override
            public void done(BmobQueryResult<Teacher_Course> bmobQueryResult, BmobException e) {
                List<Teacher_Course> list = bmobQueryResult.getResults();
                if (null != list) {
                    if (list.size() > 0) {
                        Teacher_Course teacher_course = list.get(0);

                        int numbers = teacher_course.getNumbers();
                        teacher_course.setNumbers(++numbers);
                        teacher_course.update(context);

                        final Student_Course course = new Student_Course();
                        course.setC_id(teacher_course.getC_id());
                        course.setName(teacher_course.getName());
                        course.setAccount(account);
                        course.setTeacher(teacher_course.getT_name());
                        course.setCode(code);
                        course.save(context, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                resultCallback.onSuccess(course);
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                resultCallback.onFailure(s);
                            }
                        });
                    } else {
                        resultCallback.onSuccess(null);
                    }
                } else {
                    resultCallback.onFailure(e.getMessage());

                }
            }
        }, code);

    }
}
