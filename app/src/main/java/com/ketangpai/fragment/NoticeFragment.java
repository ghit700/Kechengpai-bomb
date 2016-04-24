package com.ketangpai.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ketangpai.adapter.DataAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.bean.DataFile;
import com.ketangpai.bean.Notice;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.TimeUtils;
import com.ketangpai.view.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016/4/15.
 */
public class NoticeFragment extends BaseFragment {

    //view
    private TextView tv_notice_time;
    private TextView tv_notice_content;
    private RecyclerView list_notice_data;

    //adapter
    private DataAdapter mDataAdapter;

    //变量
    private List<DataFile> mDataList;
    private Notice mNotice;
    private List<DataFile> mFiles;

    @Override
    protected void initVarious() {
        super.initVarious();
        if (null != getActivity().getIntent().getSerializableExtra("notice")) {
            mNotice = (Notice) getActivity().getIntent().getSerializableExtra("notice");

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notice;
    }

    @Override
    protected void initView() {
        tv_notice_time = (TextView) view.findViewById(R.id.tv_notice_publishTime);
        tv_notice_content = (TextView) view.findViewById(R.id.tv_notice_content);
        initNoticeDataList();
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

    private void initNoticeDataList() {
        list_notice_data = (RecyclerView) view.findViewById(R.id.list_notice_data);
        list_notice_data.setLayoutManager(new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mDataList = new ArrayList<DataFile>();
//        for (int i = 0; i <file_names.length ; ++i) {
//            DataFile
//        }

    }
}
