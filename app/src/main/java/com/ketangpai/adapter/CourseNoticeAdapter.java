package com.ketangpai.adapter;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.bean.Notice;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.TimeUtils;
import com.ketangpai.view.MyPopupMenu;

import java.util.List;

/**
 * Created by nan on 2016/3/17.
 */
public class CourseNoticeAdapter extends BaseAdapter<Notice> {




    public CourseNoticeAdapter(Context mContext, List<Notice> mDataList) {
        super(mContext, mDataList);

    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_course_notice;
    }


    @Override
    protected void bindData(ViewHolder holder, final int position, final Notice s) {
        TextView mNoticeTitleText = (TextView) holder.getViewById(R.id.tv_notice_title);
        TextView mNoticeTimeText = (TextView) holder.getViewById(R.id.tv_notice_publishTime);
        TextView mNoticeContentText = (TextView) holder.getViewById(R.id.tv_notice_content);


        //初始化值
        mNoticeTitleText.setText(s.getTitle());
        mNoticeContentText.setText(s.getContent());
        mNoticeTimeText.setText(TimeUtils.getNoticeTime(s.getTime()));
    }


}
