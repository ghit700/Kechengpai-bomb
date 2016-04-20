package com.ketangpai.fragment;

import android.widget.TextView;

import com.ketangpai.base.BaseFragment;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.TimeUtils;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/4/15.
 */
public class NoticeFragment extends BaseFragment {

    private TextView tv_notice_time;
    private TextView tv_notice_content;

    @Override
    protected int getLayoutId() {

        return R.layout.fragment_notice;
    }

    @Override
    protected void initView() {
        tv_notice_time = (TextView) view.findViewById(R.id.tv_notice_publishTime);
        tv_notice_content = (TextView) view.findViewById(R.id.tv_notice_content);

    }

    @Override
    protected void initData() {
        Calendar calendar = Calendar.getInstance();
        tv_notice_time.setText(TimeUtils.getCurrentDateFormat(calendar) + "  " + TimeUtils.getCurrentTimeFormat(calendar));

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {

    }
}
