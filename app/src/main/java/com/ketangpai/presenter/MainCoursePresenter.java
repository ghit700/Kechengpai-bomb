package com.ketangpai.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ketangpai.base.BasePresenter;
import com.ketangpai.bean.Course;
import com.ketangpai.fragment.MainCourseFragment;
import com.ketangpai.model.CourseModel;
import com.ketangpai.modelImpl.CourseModelImpl;
import com.ketangpai.utils.VolleyUtils;
import com.ketangpai.viewInterface.MainCourseViewInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class MainCoursePresenter extends BasePresenter<MainCourseViewInterface> {
    CourseModel courseModel;
    MainCourseViewInterface mainCourseViewInterface;

    public MainCoursePresenter() {
        courseModel = new CourseModelImpl();
    }

    public void getCourseList(String account, int type) {
        if (isViewAttached()) {
            mainCourseViewInterface = getView();
            courseModel.queryCourseList(account, type, new VolleyUtils.ResultCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.i(MainCourseFragment.TAG, "getCourseList  " + result);
                    if (!result.equals("-1")) {
                        List<Course> list = new ArrayList<Course>(JSONArray.parseArray(result, Course.class));
                        mainCourseViewInterface.getCourseListOnComplete(list);
                    } else {
                        mainCourseViewInterface.getCourseListOnComplete(null);
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        } else {
            Log.i(MainCourseFragment.TAG, "没有连接到view");
        }

    }
}
