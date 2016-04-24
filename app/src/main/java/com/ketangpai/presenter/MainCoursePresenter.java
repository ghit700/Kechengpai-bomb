package com.ketangpai.presenter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.ketangpai.Callback.ResultCallback;
import com.ketangpai.Callback.ResultsCallback;
import com.ketangpai.base.BasePresenter;
import com.ketangpai.bean.Student_Course;
import com.ketangpai.bean.Teacher_Course;
import com.ketangpai.fragment.MainCourseFragment;
import com.ketangpai.model.CourseModel;
import com.ketangpai.modelImpl.CourseModelImpl;
import com.ketangpai.viewInterface.MainCourseViewInterface;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/4/19.
 */
public class MainCoursePresenter extends BasePresenter<MainCourseViewInterface> {
    CourseModel courseModel;
    MainCourseViewInterface mainCourseViewInterface;

    public MainCoursePresenter() {
        courseModel = new CourseModelImpl();
    }

    public void getCourseList(Context context, String account, int type) {
        if (isViewAttached()) {
            mainCourseViewInterface = getView();
            courseModel.queryCourseList(context, account, type, new ResultsCallback() {
                @Override
                public void onSuccess(List list) {
                    if (list.size() > 0) {
                        mainCourseViewInterface.getCourseListOnComplete(list);
                    } else {
                        mainCourseViewInterface.getCourseListOnComplete(null);
                    }
                }

                @Override
                public void onFailure(BmobException e) {
                    Log.i(MainCourseFragment.TAG, "getCourseList " + e.getMessage());
                }
            });
        } else {
            Log.i(MainCourseFragment.TAG, "没有连接到view");
        }

    }

    public void createCourse(Context context, final Teacher_Course course) {
        if (isViewAttached()) {
            mainCourseViewInterface = getView();
            mainCourseViewInterface.showLoading(0);

            courseModel.createCourse(context, course, new ResultCallback() {
                @Override
                public void onSuccess(Object object) {
                    mainCourseViewInterface.createCourseOnComplete((Teacher_Course) object);
                    mainCourseViewInterface.hideLoading();
                }

                @Override
                public void onFailure(String e) {
                    Log.i(MainCourseFragment.TAG, "createCourse " + e);
                    mainCourseViewInterface.createCourseOnComplete(null);
                    mainCourseViewInterface.hideLoading();
                }
            });


        } else {
            Log.i(MainCourseFragment.TAG, "没有连接到view");
        }
    }

    public void addCourse(final Context context, String code, String acccount) {
        if (isViewAttached()) {
            mainCourseViewInterface = getView();
            mainCourseViewInterface.showLoading(1);
            courseModel.addCourse(context, code, acccount, new ResultCallback() {
                @Override
                public void onSuccess(Object object) {
                    mainCourseViewInterface.addCourseOnComplete((Student_Course) object);
                    mainCourseViewInterface.hideLoading();
                }

                @Override
                public void onFailure(String e) {
                    Log.i(MainCourseFragment.TAG, "addCourse " + e);
                    mainCourseViewInterface.addCourseOnComplete(null);
                    mainCourseViewInterface.hideLoading();

                }
            });
        } else {
            Log.i(MainCourseFragment.TAG, "没有连接到view");

        }

    }
}
