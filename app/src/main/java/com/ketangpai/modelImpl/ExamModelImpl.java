package com.ketangpai.modelImpl;

import android.content.Context;
import android.util.Log;

import com.ketangpai.bean.Student_Course;
import com.ketangpai.bean.Student_Reply;
import com.ketangpai.bean.Subject;
import com.ketangpai.bean.Test;
import com.ketangpai.callback.ResultCallback;
import com.ketangpai.callback.ResultsCallback;
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
    private boolean gradeFlag;


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
    public void getStudentExam(Context context, Test test, String account, final ResultCallback resultCallback) {
        Log.i("======getStudentExam", test.getT_id() + "   " + account);
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
        }, test.getT_id(), account);
    }

    @Override
    public void publishStudentExam(final Context context, final Student_Reply student_reply) {
        gradeFlag = true;
        int grade = 0;
        List<Subject> subjects = student_reply.getSubjects();
        for (Subject s : subjects) {
            switch (s.getType()) {
                case 1:
                    if (s.getAnswer().equals(s.getSolution())) {
                        s.setS_value(s.getScore());
                        grade += s.getScore();
                    } else {
                        s.setS_value(0);
                    }
                    break;
                case 2:
                    if (s.getAnswer().equals(s.getSolution())) {
                        s.setS_value(s.getScore());
                        grade += s.getScore();

                    } else {
                        s.setS_value(0);
                    }
                    break;
                case 3:
                    if (s.getAnswer().equals(s.getSolution())) {
                        s.setS_value(s.getScore());
                        grade += s.getScore();
                    } else {
                        s.setS_value(0);
                    }
                    break;
                case 4:
                    gradeFlag = false;
                    break;
            }
        }
        if (gradeFlag) {
            student_reply.setGrade(grade);
            student_reply.setT_state("已批改");
        }

        student_reply.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                BmobQuery<Test> query = new BmobQuery<Test>();
                query.addWhereEqualTo("t_id", student_reply.getT_id());
                query.findObjects(context, new FindListener<Test>() {
                    @Override
                    public void onSuccess(List<Test> list) {
                        if (null != list && list.size() > 0) {
                            Test test = list.get(0);
                            int no_hander = test.getNo_hander_count();
                            no_hander--;
                            test.setNo_hander_count(no_hander);
                            if (gradeFlag) {
                                int check_count = test.getCheck_count();
                                check_count++;
                                test.setCheck_count(check_count);

                            } else {
                                int no_check_count = test.getNo_check_count();
                                no_check_count++;
                                test.setNo_check_count(no_check_count);

                            }
                            test.update(context);

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
