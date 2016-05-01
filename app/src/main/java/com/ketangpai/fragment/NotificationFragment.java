package com.ketangpai.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ketangpai.adapter.NotificationAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.base.BasePresenterFragment;
import com.ketangpai.bean.Notification;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.presenter.NotificationPresenter;
import com.ketangpai.viewInterface.NotificationViewInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nan on 2016/4/9.
 */
public class NotificationFragment extends BasePresenterFragment<NotificationViewInterface, NotificationPresenter> implements NotificationViewInterface, OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = "===NotificationFragment";
    //view
    private RecyclerView mList_notification;
    private SwipeRefreshLayout mRefreshLayout;

    //adapter
    private NotificationAdapter mNotificationAdapter;

    //variables
    private List<Notification> mNotificationList;

    private String account;

    @Override
    protected void initVarious() {
        super.initVarious();
        account = mContext.getSharedPreferences("user", 0).getString("account", "");
    }

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
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
        onRefresh();

//        Notification notification_ = new Notification();
//        notification_.setTime("111");
//        List<String> messages = new ArrayList<>();
//        for (int i = 0; i < 3; ++i) {
//            messages.add("111" + i);
//        }
//        notification_.setCourses(messages);
//        for (int i = 0; i < 10; ++i) {
//            mNotificationAdapter.addItem(i, notification_);
//        }
    }

    @Override
    protected void initListener() {
        mNotificationAdapter.setOnItemClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void loadData() {

    }

    private void initNotificationList() {
        mRefreshLayout.setRefreshing(false);
        mList_notification = (RecyclerView) view.findViewById(R.id.list_notification);
        mList_notification.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mNotificationList = new ArrayList();
        mNotificationAdapter = new NotificationAdapter(mContext, mNotificationList);
        mList_notification.setAdapter(mNotificationAdapter);

    }

    @Override
    public void onItemClick(View view, int position) {
    }

    @Override
    public void getNotificationListOnComplete(List<Notification> notifications) {
        mRefreshLayout.setRefreshing(false);
        mNotificationList.addAll(notifications);
        mNotificationAdapter.reorderSections();
        mNotificationAdapter.notifyDataSetChanged();
    }

    @Override
    protected NotificationPresenter createPresenter() {
        return new NotificationPresenter();
    }

    @Override
    public void onRefresh() {
        mPresenter.getNotificationList(mContext, account);
    }
}
