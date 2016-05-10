package com.ketangpai.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ketangpai.activity.ChatActivity;
import com.ketangpai.activity.MainActivity;
import com.ketangpai.adapter.MessageAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.bean.NewestMessage;
import com.ketangpai.bean.User;
import com.ketangpai.callback.ResultsCallback;
import com.ketangpai.event.NotificationEvent;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.modelImpl.MessageModelImpl;
import com.ketangpai.nan.ketangpai.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by nan on 2016/4/17.
 */
public class MessageFragment extends BaseFragment implements OnItemClickListener {

    //view
    RecyclerView list_messages;

    //adater
    MessageAdapter mMessageAdapter;

    //变量
    private String account;
    List<NewestMessage> mMessages;
    private MessageModelImpl mMessageModel;

    @Override
    protected void initVarious() {
        super.initVarious();
        account = mContext.getSharedPreferences("user", 0).getString("account", "");
        mMessageModel = new MessageModelImpl();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {
        list_messages = (RecyclerView) view.findViewById(R.id.list_messagme);

        list_messages.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mMessages = new ArrayList();
        mMessageAdapter = new MessageAdapter(mContext, mMessages, account);
        list_messages.setAdapter(mMessageAdapter);
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initListener() {
        mMessageAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {
        getNewestMessageLis(account);
    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(mContext, ChatActivity.class);
        NewestMessage newestMessage = mMessages.get(position);
        if (newestMessage.getReceive_account().equals(account)) {
            intent.putExtra("send_user", new User(newestMessage.getSend_account(), newestMessage.getSend_name(), newestMessage.getSend_path()));
        } else {
            intent.putExtra("send_user", new User(newestMessage.getReceive_account(), newestMessage.getReceive_name(), newestMessage.getReceive_path()));
        }
        startActivity(intent);

    }

    @Subscribe
    public void onNotificationEvent(NotificationEvent event) {
        ((MainActivity) getActivity()).setNotifyOn();
    }


    public void getNewestMessageLis(String account) {

        mMessageModel.getNewestMessageList(mContext, account, new ResultsCallback() {
            @Override
            public void onSuccess(List list) {
                mMessages.clear();
                mMessages.addAll(list);
                Collections.reverse(mMessages);
                mMessageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(BmobException e) {

            }
        });

    }


}
