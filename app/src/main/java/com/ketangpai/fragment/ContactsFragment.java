package com.ketangpai.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.ketangpai.activity.ChatActivity;
import com.ketangpai.activity.MainActivity;
import com.ketangpai.adapter.ContactsExAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.event.NotificationEvent;
import com.ketangpai.nan.ketangpai.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by nan on 2016/3/15.
 */
public class ContactsFragment extends BaseFragment implements ExpandableListView.OnChildClickListener {

    //view
    ExpandableListView mMessageExList;

    //adpter
    ContactsExAdapter mContactsExAdapter;

    //变量
    ArrayList<String> mGroupNames;
    ArrayList<ArrayList<String>> mGroupItemUsers;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contacts;
    }

    @Override
    protected void initVarious() {
        super.initVarious();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView() {
        mMessageExList = (ExpandableListView) view.findViewById(R.id.exlist_messagme);
        initMessageExList();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mMessageExList.setOnChildClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    private void initMessageExList() {
        mGroupNames = new ArrayList<>();
        mGroupItemUsers = new ArrayList<>();
        mContactsExAdapter = new ContactsExAdapter(mContext, mGroupNames, mGroupItemUsers);
        mMessageExList.setAdapter(mContactsExAdapter);

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Intent intent=new Intent(mContext, ChatActivity.class);
        startActivity(intent);
        return true;
    }

    @Subscribe
    public void onNotificationEvent(NotificationEvent event) {
        Log.i("=====","1111");
        ((MainActivity)getActivity()).setNotifyOn();
    }

}
