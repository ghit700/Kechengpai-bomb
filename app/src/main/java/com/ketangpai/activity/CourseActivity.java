package com.ketangpai.activity;

import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ketangpai.base.BasePresenter;
import com.ketangpai.base.DrawerBaseActivity;
import com.ketangpai.fragment.CourseFragment;
import com.ketangpai.nan.ketangpai.R;

/**
 * Created by nan on 2016/3/16.
 */
public class CourseActivity extends DrawerBaseActivity {


    //view


    //变量
    private String mCourseName;
    private int mC_id;
    private CourseFragment mFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_nevigation;
    }

    @Override
    protected void initVariables() {
        super.initVariables();
        if (getIntent().getStringExtra("course") != null) {
            mCourseName = getIntent().getStringExtra("course");
            mC_id = getIntent().getIntExtra("c_id", -1);

        }

    }

    @Override
    protected Fragment getLayoutFragment() {
        mFragment = new CourseFragment();
        return mFragment;
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            default:
                break;
        }
    }


    @Override
    protected void initToolBar() {
        super.initToolBar();
        getSupportActionBar().setTitle(mCourseName);
    }

    public int getC_id() {
        return mC_id;
    }


}
