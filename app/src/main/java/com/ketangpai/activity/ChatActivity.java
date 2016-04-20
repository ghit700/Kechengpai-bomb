package com.ketangpai.activity;

import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ketangpai.base.BasePresenter;
import com.ketangpai.base.BaseToolbarActivity;
import com.ketangpai.base.DrawerBaseActivity;
import com.ketangpai.fragment.ChatFragment;
import com.ketangpai.nan.ketangpai.R;

/**
 * Created by nan on 2016/3/18.
 */
public class ChatActivity extends BaseToolbarActivity {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_nevigation;
    }

    @Override
    protected Fragment getLayoutFragment() {
        return new ChatFragment();
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void loadData() {

    }





}
