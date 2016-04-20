package com.ketangpai.activity;

import android.support.v4.app.Fragment;

import com.ketangpai.base.BaseToolbarActivity;
import com.ketangpai.fragment.AccountUpdateFragment;
import com.ketangpai.nan.ketangpai.R;

/**
 * Created by Administrator on 2016/4/19.
 */
public class AccountUpdateActivity extends BaseToolbarActivity {

    private String mColunmnName;

    public static final int RESULT_OK = 100;

    @Override
    protected Fragment getLayoutFragment() {
        return new AccountUpdateFragment();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_nevigation;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        if (getIntent().getStringExtra("columnName") != null) {
            mColunmnName = getIntent().getStringExtra("columnName");
            getSupportActionBar().setTitle("修改" + mColunmnName);
        }
    }

    public String getColunmnName() {
        return mColunmnName;
    }
}
