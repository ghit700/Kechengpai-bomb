package com.ketangpai.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.bean.Course;
import com.ketangpai.bean.Student_Course;
import com.ketangpai.bean.Teacher_Course;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.view.MyPopupMenu;

import java.util.List;

import cn.bmob.v3.listener.DeleteListener;

/**
 * Created by nan on 2016/3/14.
 */
public class CourseSMainCourseAdapter extends BaseAdapter<Course> {

    public CourseSMainCourseAdapter(Context mContext, List<Course> mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_main_student_course;
    }

    @Override
    protected void bindData(ViewHolder holder, final int position, Course s) {
        //初始化view
        TextView CourseName = (TextView) holder.getViewById(R.id.tv_item_courseName);
        TextView CourseCode = (TextView) holder.getViewById(R.id.tv_item_courseCode);
        TextView StudentName = (TextView) holder.getViewById(R.id.tv_item_StudentName);


        //初始化view的值
        StudentName.setText(((Student_Course) s).getTeacher());
        CourseName.setText(s.getName());
        CourseCode.setText(s.getCode());

    }


}
