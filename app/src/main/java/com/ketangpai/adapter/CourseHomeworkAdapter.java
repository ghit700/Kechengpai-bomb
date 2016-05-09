package com.ketangpai.adapter;

import android.content.Context;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.bean.Teacher_Homework;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.TimeUtils;

import java.util.List;

/**
 * Created by nan on 2016/3/16.
 */
public class CourseHomeworkAdapter extends BaseAdapter<Teacher_Homework> {


    public CourseHomeworkAdapter(Context mContext, List<Teacher_Homework> mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {


        return R.layout.item_course_homework;

    }

    @Override
    protected void bindData(ViewHolder holder, int position, Teacher_Homework s) {
        //初始化view
        TextView mTypeText = (TextView) holder.getViewById(R.id.tv_t_homework_type);
        TextView mPublishTimeText = (TextView) holder.getViewById(R.id.tv_t_homework_publishTime);
        TextView mEndTimeText = (TextView) holder.getViewById(R.id.tv_t_homework_endTime);
        TextView mTitleText = (TextView) holder.getViewById(R.id.tv_t_homework_title);
        TextView mContentText = (TextView) holder.getViewById(R.id.tv_t_homework_content);
        TextView mAccessoryText = (TextView) holder.getViewById(R.id.tv_t_homework_accessory);



        //初始化view的值
        mPublishTimeText.setText(TimeUtils.getNoticeTime(s.getP_time()));
        mEndTimeText.setText("截至：" + TimeUtils.getNoticeTime(s.getE_time()));
        mTitleText.setText(s.getTitle());
        mContentText.setText(s.getContent());

        if (0 < s.getFiles().size()) {
            mAccessoryText.setText(s.getFiles().size() + "个附件");
        } else {
            mAccessoryText.setText("没有附件");

        }
    }


}
