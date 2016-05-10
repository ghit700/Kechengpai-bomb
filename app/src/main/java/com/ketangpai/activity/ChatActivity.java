package com.ketangpai.activity;

import android.support.v4.app.Fragment;

import com.ketangpai.base.BaseToolbarActivity;
import com.ketangpai.bean.User;
import com.ketangpai.fragment.ChatFragment;
import com.ketangpai.nan.ketangpai.R;

/**
 * Created by nan on 2016/3/18.
 */
public class ChatActivity extends BaseToolbarActivity {

    private User mSend_User;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_course_add_tab;
    }

    @Override
    protected Fragment getLayoutFragment() {
        return new ChatFragment();
    }

    @Override
    protected void initVariables() {
        super.initVariables();
        mSend_User = (User) getIntent().getSerializableExtra("send_user");
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setTitle(mSend_User.getName());
    }
}
