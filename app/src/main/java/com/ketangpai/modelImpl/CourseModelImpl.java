package com.ketangpai.modelImpl;

import android.util.Log;

import com.ketangpai.bean.Course;
import com.ketangpai.constant.Constant;
import com.ketangpai.constant.Urls;
import com.ketangpai.fragment.MainCourseFragment;
import com.ketangpai.model.CourseModel;
import com.ketangpai.utils.VolleyUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class CourseModelImpl implements CourseModel {
    HashMap<String, String> params;

    @Override
    public List<Course> queryCourseList(String account, int type, VolleyUtils.ResultCallback resultCallback) {
        params = new HashMap<>();
        params.put("account", account);
        params.put("type", String.valueOf(type));

        Log.i(MainCourseFragment.TAG, "queryCourseList account=" + account + " type=" + type);
        String url = Urls.SERVER_HOST + Urls.QUERY_COURSE_LIST;
        VolleyUtils.post(url, MainCourseFragment.TAG, params, resultCallback);
        return null;
    }
}
