package com.ketangpai.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ketangpai.adapter.ChatAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.bean.MessageInfo;
import com.ketangpai.bean.User;
import com.ketangpai.callback.ResultCallback;
import com.ketangpai.callback.ResultsCallback;
import com.ketangpai.event.ReceiveMessageEvent;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.model.MessageModel;
import com.ketangpai.modelImpl.MessageModelImpl;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.PushManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by nan on 2016/3/19.
 */
public class ChatFragment extends BaseFragment implements View.OnClickListener, TextWatcher, OnItemClickListener {

    //view
    EditText mSendTextEt;
    ImageView mSendtBtn;
    RecyclerView mChatList;
    RelativeLayout mRl;

    //adapter
    ChatAdapter mChatAdapter;

    //变量
    List<MessageInfo> mChatRecondList;
    private String name;
    private String path;
    private String account;
    private User mSend_User;
    private InputMethodManager mImm;
    private MessageModel mMessageModel;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void initVarious() {
        super.initVarious();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        EventBus.getDefault().register(this);
        mImm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        account = mContext.getSharedPreferences("user", 0).getString("account", "");
        name = mContext.getSharedPreferences("user", 0).getString("name", "");
        path = mContext.getSharedPreferences("user", 0).getString("path", "");
        if (null != getActivity().getIntent().getSerializableExtra("send_user")) {
            mSend_User = (User) getActivity().getIntent().getSerializableExtra("send_user");
        }
        mMessageModel = new MessageModelImpl();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView() {
        mSendtBtn = (ImageView) view.findViewById(R.id.img_chat_send);
        mSendTextEt = (EditText) view.findViewById(R.id.et_chat_sendText);
        mRl = (RelativeLayout) view.findViewById(R.id.rl_chat);
        initChatList();


    }

    @Override
    protected void initData() {

        mChatList.scrollToPosition(9);
    }

    @Override
    protected void initListener() {
        mSendTextEt.setOnClickListener(this);
        mSendTextEt.addTextChangedListener(this);
        mSendtBtn.setOnClickListener(this);
        mRl.setOnClickListener(this);
        mChatAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {
        getChatRecondList(account, mSend_User.getAccount());
    }

    private void initChatList() {
        mChatList = (RecyclerView) view.findViewById(R.id.list_chat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mChatList.setLayoutManager(linearLayoutManager);
        mChatRecondList = new ArrayList();
        mChatAdapter = new ChatAdapter(mContext, mChatRecondList, account, path);
        mChatList.setAdapter(mChatAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_chat_sendText:
                break;
            case R.id.img_chat_send:
                sendMessage();
                break;
            case R.id.rl_chat:
                mImm.hideSoftInputFromInputMethod(v.getWindowToken(), 0);
                break;
            default:
                break;
        }
    }

    private void sendMessage() {
        String content = mSendTextEt.getText().toString();
        mSendTextEt.setText("");
        if (!content.equals("")) {
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setContent(content);
            messageInfo.setTime(System.currentTimeMillis());
            messageInfo.setReceive_account(mSend_User.getAccount());
            messageInfo.setReceive_name(mSend_User.getName());
            messageInfo.setSend_account(account);
            messageInfo.setSend_name(name);
            messageInfo.setSend_path(path);
            mChatAdapter.addItem(mChatRecondList.size(), messageInfo);
            sendMessage(messageInfo, mSend_User.getPath());
            mChatList.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mChatList.smoothScrollToPosition(mChatRecondList.size());
                }
            }, 200);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mSendTextEt.length() == 0) {
            mSendtBtn.setImageResource(R.drawable.ic_send_default);
        } else {
            mSendtBtn.setImageResource(R.drawable.ic_send_light);
        }
    }


    public void getChatRecondList(String account, String send_account) {

        mMessageModel.getChatRecondList(mContext, account, send_account, new ResultsCallback() {
            @Override
            public void onSuccess(List list) {
                mChatRecondList.addAll(list);
                mChatAdapter.notifyDataSetChanged();
                mChatList.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mChatList.smoothScrollToPosition(mChatRecondList.size());
                    }
                }, 200);
            }

            @Override
            public void onFailure(BmobException e) {

            }
        });

    }

    ;

    public void sendMessage(MessageInfo messageInfo, String path) {

        mMessageModel.sendMessage(mContext, messageInfo, path, new ResultCallback() {
            @Override
            public void onSuccess(Object object) {

                PushManager.sendMessage(mContext, (MessageInfo) object);
            }

            @Override
            public void onFailure(String e) {

            }
        });


    }


    @Subscribe
    public void onReceiveMessageEvent(ReceiveMessageEvent event) {
        mChatAdapter.addItem(mChatRecondList.size(), event.getMessageInfo());
        mChatList.postDelayed(new Runnable() {
            @Override
            public void run() {
                mChatList.smoothScrollToPosition(mChatRecondList.size());
            }
        }, 200);
    }

    @Override
    public void onItemClick(View view, int position) {
        mImm.hideSoftInputFromInputMethod(view.getWindowToken(), 0);

    }
}
