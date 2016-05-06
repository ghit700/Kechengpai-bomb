package com.ketangpai.modelImpl;

import android.content.Context;
import android.util.Log;

import com.ketangpai.bean.Student_Course;
import com.ketangpai.bean.Student_Homework;
import com.ketangpai.bean.Student_Reply;
import com.ketangpai.bean.Subject;
import com.ketangpai.bean.Teacher_Homework;
import com.ketangpai.bean.Test;
import com.ketangpai.callback.ResultCallback;
import com.ketangpai.callback.ResultsCallback;
import com.ketangpai.fragment.AddExamFragment;
import com.ketangpai.fragment.AddHomeworkFragment;
import com.ketangpai.fragment.THomeworkFragment;
import com.ketangpai.model.ExamModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by nan on 2016/5/5.
 */
public class ExamModelImpl implements ExamModel {
    @Override
    public void publishExam(Context context, Test test, SaveListener resultCallback) {
        test.save(context, resultCallback);
        List<Subject> subjects = test.getSubjects();
        for (Subject s : subjects) {
            s.save(context);
        }
    }

    @Override
    public void createStudentExamList(final Context context, final Test test) {
        BmobQuery<Student_Course> query = new BmobQuery<>();
        query.addWhereEqualTo("c_id", test.getC_id());
        query.findObjects(context, new FindListener<Student_Course>() {
            @Override
            public void onSuccess(List<Student_Course> list) {
                if (null != list && list.size() > 0) {
                    for (Student_Course course : list) {
                        Student_Reply student_reply = new Student_Reply();
                        student_reply.setStudent_number(course.getStudent_number());
                        student_reply.setStudent_name(course.getStudent_name());
                        student_reply.setAccount(course.getAccount());
                        student_reply.setC_id(course.getC_id());
                        student_reply.setT_id(test.getT_id());
                        student_reply.addAllUnique("subjects", test.getSubjects());
                        student_reply.setS_state("未交");
                        student_reply.setT_state("未交");
                        student_reply.save(context);
                    }

                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    @Override
    public void getExamList(Context context, int c_id, final ResultsCallback resultsCallback) {
        BmobQuery<Test> query = new BmobQuery<>();
        query.addWhereEqualTo("c_id", c_id);
        query.findObjects(context, new FindListener<Test>() {
            @Override
            public void onSuccess(List<Test> list) {
                if (null != list && list.size() > 0) {
                    resultsCallback.onSuccess(list);
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.i(AddExamFragment.TAG, "getExamList " + s);
            }
        });

    }

    @Override
    public void getStudentExamList(Context context, int t_id, final ResultsCallback resultsCallback) {
        BmobQuery<Student_Reply> query = new BmobQuery<>();
        query.addWhereEqualTo("t_id", t_id);
        query.findObjects(context, new FindListener<Student_Reply>() {
            @Override
            public void onSuccess(List<Student_Reply> list) {
                if (null != list && list.size() > 0) {
                    resultsCallback.onSuccess(list);
                }
            }

            @Override
            public void onError(int i, String s) {
                Log.i("====", "getStudentExamList " + s);
            }
        });
    }

    @Override
    public void getExamListToStudent(Context context, int c_id, long add_time, final ResultsCallback resultsCallback) {
        String sql = "select * from Test where c_id =? and p_time>?";

        BmobQuery<Test> query = new BmobQuery();

        query.doSQLQuery(context, sql, new SQLQueryListener<Test>() {
            @Override
            public void done(BmobQueryResult<Test> bmobQueryResult, BmobException e) {
                List<Test> tests = bmobQueryResult.getResults();
                if (null != tests && tests.size() > 0) {
                    resultsCallback.onSuccess(tests);
                } else {
                    resultsCallback.onFailure(e);
                }

            }
        }, c_id, add_time);
    }

    @Override
    public void correctExam(Context context, Student_Reply student_reply, UpdateListener resultCallback) {

    }

    @Override
    public void getStudentExam(Context context, Test test, String account, final ResultCallback resultCallback) {
        String sql = "select * from Student_Reply where t_id=? and account=?";
        BmobQuery<Student_Reply> query = new BmobQuery<>();
        query.doSQLQuery(context, sql, new SQLQueryListener<Student_Reply>() {
            @Override
            public void done(BmobQueryResult<Student_Reply> bmobQueryResult, BmobException e) {
                List<Student_Reply> student_replies = bmobQueryResult.getResults();
                if (null != student_replies && student_replies.size() > 0) {
                    resultCallback.onSuccess(student_replies.get(0));
                }
            }
        }, test.getC_id(), account);
    }

    @Override
    public void publishStudentExam(Context context, Student_Reply student_reply) {

    }
}
