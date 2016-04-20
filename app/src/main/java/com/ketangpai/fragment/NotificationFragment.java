package com.ketangpai.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ketangpai.adapter.NotificationAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.bean.Notification_message;
import com.ketangpai.constant.Constant;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.nan.ketangpai.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nan on 2016/4/9.
 */
public class NotificationFragment extends BaseFragment implements OnItemClickListener {

    //view
    private RecyclerView mList_notification;
    private SwipeRefreshLayout mRefreshLayout;

    //adapter
    private NotificationAdapter mNotificationAdapter;

    //variables
    private List mNotificationList;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notification;
    }

    @Override
    protected void initView() {
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fresh_notification);
        initNotificationList();
    }

    @Override
    protected void initData() {
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        Notification_message notification_message = new Notification_message();
        notification_message.setTime("111");
        List<String> messages = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            messages.add("111" + i);
        }
        notification_message.setCourses(messages);
        for (int i = 0; i < 10; ++i) {
            mNotificationAdapter.addItem(i,notification_message );
        }
    }

    @Override
    protected void initListener() {
        mNotificationAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    private void initNotificationList() {
        mList_notification = (RecyclerView) view.findViewById(R.id.list_notification);
        mList_notification.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mNotificationList = new ArrayList();
        mNotificationAdapter = new NotificationAdapter(mContext, mNotificationList);
        mList_notification.setAdapter(mNotificationAdapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i(Constant.LOG_TAG, view.toString());
    }
}
