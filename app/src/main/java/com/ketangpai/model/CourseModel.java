package com.ketangpai.model;

import com.ketangpai.bean.Course;
import com.ketangpai.utils.VolleyUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public interface CourseModel {
    List<Course> queryCourseList(String account, int type, VolleyUtils.ResultCallback resultCallback);
}
