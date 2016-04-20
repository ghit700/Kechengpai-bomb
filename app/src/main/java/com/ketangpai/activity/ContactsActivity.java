package com.ketangpai.activity;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.ketangpai.base.BasePresenter;
import com.ketangpai.base.BaseToolbarActivity;
import com.ketangpai.fragment.ContactsFragment;
import com.ketangpai.nan.ketangpai.R;

/**
 * Created by nan on 2016/4/17.
 */
public class ContactsActivity extends BaseToolbarActivity {
    @Override
    protected Fragment getLayoutFragment() {
        return new ContactsFragment();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_nevigation;
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
        super.loadData();
    }


    @Override
    protected void initToolbar() {
        super.initToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
