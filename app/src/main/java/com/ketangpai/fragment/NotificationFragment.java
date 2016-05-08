package com.ketangpai.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ketangpai.activity.DataActivity;
import com.ketangpai.activity.ExamActivity;
import com.ketangpai.activity.HomeWorkActivity;
import com.ketangpai.activity.NoticeActivity;
import com.ketangpai.adapter.NotificationAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.base.BasePresenterFragment;
import com.ketangpai.bean.Data;
import com.ketangpai.bean.Notice;
import com.ketangpai.bean.Notification;
import com.ketangpai.bean.NotificationInfo;
import com.ketangpai.bean.Teacher_Homework;
import com.ketangpai.bean.Test;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.presenter.NotificationPresenter;
import com.ketangpai.utils.NetUtils;
import com.ketangpai.viewInterface.NotificationViewInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by nan on 2016/4/9.
 */
public class NotificationFragment extends BasePresenterFragment<NotificationViewInterface, NotificationPresenter> implements NotificationViewInterface, SwipeRefreshLayout.OnRefreshListener, NotificationAdapter.OnItemClickListener {
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


    }

    @Override
    protected void initListener() {
        mNotificationAdapter.setOnItemClickListener(this);
        mRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void loadData() {
        if (NetUtils.hasNetworkConnection()) {

            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(true);
                }
            });
            onRefresh();
        } else {
            sendToast("没有网络连接");
        }
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
    public void getNotificationListOnComplete(List<Notification> notifications) {
        mRefreshLayout.setRefreshing(false);
        mNotificationList.clear();
        mNotificationList.addAll(notifications);
//        Collections.reverse(mNotificationList);
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

    @Override
    public void onItemClick(NotificationInfo notificationInfo) {
        switch (notificationInfo.getType()) {
            case 1:
                BmobQuery<Notice> query = new BmobQuery<>();
                query.addWhereEqualTo("n_id", notificationInfo.getType_id());
                query.findObjects(mContext, new FindListener<Notice>() {
                    @Override
                    public void onSuccess(List<Notice> list) {
                        Notice notice = list.get(0);
                        Intent intent = new Intent(mContext, NoticeActivity.class);
                        intent.putExtra("notice", notice);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
                break;
            case 2:
                BmobQuery<Teacher_Homework> query1 = new BmobQuery<>();
                query1.addWhereEqualTo("h_id", notificationInfo.getType_id());
                query1.findObjects(mContext, new FindListener<Teacher_Homework>() {
                    @Override
                    public void onSuccess(List<Teacher_Homework> list) {
                        Teacher_Homework homework = list.get(0);
                        Intent intent = new Intent(mContext, HomeWorkActivity.class);
                        intent.putExtra("homework", homework);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });

                break;
            case 3:
                BmobQuery<Data> query2 = new BmobQuery<>();
                query2.addWhereEqualTo("d_id", notificationInfo.getType_id());
                query2.findObjects(mContext, new FindListener<Data>() {
                    @Override
                    public void onSuccess(List<Data> list) {
                        Data data = list.get(0);
                        Intent intent = new Intent(mContext, DataActivity.class);
                        intent.putExtra("name", data.getName());
                        intent.putExtra("url", data.getUrl());
                        startActivity(intent);
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
                break;
            case 4:
                BmobQuery<Test> query3 = new BmobQuery<>();
                query3.addWhereEqualTo("t_id", notificationInfo.getType_id());
                query3.findObjects(mContext, new FindListener<Test>() {
                    @Override
                    public void onSuccess(List<Test> list) {
                        Test test = list.get(0);
                        Intent intent = new Intent(mContext, ExamActivity.class);
                        intent.putExtra("test",test);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
                break;

            default:
                break;
        }
    }
}
