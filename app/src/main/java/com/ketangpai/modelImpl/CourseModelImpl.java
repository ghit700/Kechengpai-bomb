package com.ketangpai.modelImpl;

import android.content.Context;
import android.util.Log;

import com.ketangpai.bean.User_Group;
import com.ketangpai.callback.ResultCallback;
import com.ketangpai.callback.ResultsCallback;
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
    public void queryCourseList(Context context, String account, final ResultsCallback resultsCallback) {
        String sql = null;

        sql = "select * from Student_Course where account=?";
        BmobQuery<Student_Course> query = new BmobQuery<Student_Course>();
        query.doSQLQuery(context, sql, new SQLQueryListener<Student_Course>() {
            @Override
            public void done(BmobQueryResult<Student_Course> bmobQueryResult, BmobException e) {
                if (null != bmobQueryResult) {
                    List<Student_Course> list = bmobQueryResult.getResults();
                    if (null != list) {
                        resultsCallback.onSuccess(list);
                    } else {
                        resultsCallback.onFailure(e);
                    }
                } else {
                    resultsCallback.onFailure(e);
                }
            }
        }, account);

    }


    @Override
    public void addCourse(final Context context, final String code, final String account, final String name, final int number, final String path, final ResultCallback resultCallback) {
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
                        course.setStudent_name(name);
                        course.setStudent_number(number);
                        course.setAdd_time(System.currentTimeMillis());
                        course.save(context, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                User_Group user_group = new User_Group();
                                user_group.setC_name(course.getName());
                                user_group.setC_id(course.getC_id());
                                user_group.setName(course.getStudent_name());
                                user_group.setAccount(account);
                                user_group.setPath(path);
                                user_group.save(context);
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
