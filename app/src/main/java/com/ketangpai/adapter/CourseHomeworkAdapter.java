package com.ketangpai.adapter;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.bean.Teacher_Homework;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.TimeUtils;
import com.ketangpai.view.MyPopupMenu;

import java.util.List;

/**
 * Created by nan on 2016/3/16.
 */
public class CourseHomeworkAdapter extends BaseAdapter<Teacher_Homework> implements PopupMenu.OnMenuItemClickListener {

    private int type;

    public CourseHomeworkAdapter(Context mContext, List<Teacher_Homework> mDataList, int type) {
        super(mContext, mDataList);
        this.type = type;
    }

    @Override
    protected int getItemLayoutId(int viewType) {

        if (type == 0) {
            return R.layout.item_course_t_homework;
        } else {
            return R.layout.item_course_s_homework;
        }
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

        ImageView mEditImg = (ImageView) holder.getViewById(R.id.img_t_home_edit);

        if (type == 0) {
            TextView mCheckCountText = (TextView) holder.getViewById(R.id.tv_t_homework_checkCount);
            TextView mNoCheckCountText = (TextView) holder.getViewById(R.id.tv_t_homework_noCheckCount);
            TextView mNoPostCountText = (TextView) holder.getViewById(R.id.tv_t_homework_noPostCount);
            //设置事件
            //mEditImg点击事件
            final MyPopupMenu mEditPopupMenu;
            mEditPopupMenu = new MyPopupMenu(mContext, mEditImg, R.menu.t_homework_edit_menu);
            mEditPopupMenu.setOnMenuItemClickListener(this);
            mEditImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEditPopupMenu.show();
                }
            });

            mCheckCountText.setText(String.valueOf(s.getCheck_count()));
            mNoCheckCountText.setText(String.valueOf(s.getNo_check_count()));
            mNoPostCountText.setText(String.valueOf(s.getNo_hander_count()));

        }

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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.t_homework_edit_menu:
                break;
            case R.id.t_homework_delete_menu:
                break;

            default:
                break;
        }
        return true;
    }

}
