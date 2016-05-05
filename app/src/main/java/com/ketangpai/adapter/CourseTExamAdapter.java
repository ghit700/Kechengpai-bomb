package com.ketangpai.adapter;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.bean.Test;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.TimeUtils;
import com.ketangpai.view.MyPopupMenu;

import java.util.List;

/**
 * Created by nan on 2016/3/16.
 */
public class CourseTExamAdapter extends BaseAdapter<Test> {
    int type;

    public CourseTExamAdapter(Context mContext, List<Test> mDataList, int type) {
        super(mContext, mDataList);
        this.type = type;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if (type == 0) {
            return R.layout.item_course_t_exam;
        } else {
            return R.layout.item_course_s_exam;
        }
    }


    @Override
    protected void bindData(ViewHolder holder, int position, Test s) {
        //初始化view
        TextView mPublishTimeText = (TextView) holder.getViewById(R.id.tv_t_exam_publishTime);
        TextView mTitleText = (TextView) holder.getViewById(R.id.tv_t_exam_title);
        TextView mContentText = (TextView) holder.getViewById(R.id.tv_t_exam_content);
        TextView mEndTimeText = (TextView) holder.getViewById(R.id.tv_t_exam_endTime);


        if (type == 0) {
            TextView mCheckCountText = (TextView) holder.getViewById(R.id.tv_t_exam_checkCount);
            TextView mNoCheckCountText = (TextView) holder.getViewById(R.id.tv_t_exam_noCheckCount);
            TextView mNoPostCountText = (TextView) holder.getViewById(R.id.tv_t_exam_noPostCount);
            ImageView mEditImg = (ImageView) holder.getViewById(R.id.img_t_exam_edit);

            //为view设置事件
            //mEditImg的点击事件
            final MyPopupMenu mEditPopupMenu = new MyPopupMenu(mContext, mEditImg, R.menu.t_exam_edit_menu);
            mEditPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    return false;
                }
            });
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
        mEndTimeText.setText(TimeUtils.getNoticeTime(s.getE_time()));
        mTitleText.setText(s.getTitle());
        mContentText.setText(s.getContent());
    }


}
