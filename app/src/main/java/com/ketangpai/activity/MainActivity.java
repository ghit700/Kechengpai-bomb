package com.ketangpai.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ketangpai.base.DrawerBaseActivity;
import com.ketangpai.fragment.MainFragment;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.ActivityCollector;


/**
 * Created by nan on 2016/3/9.
 */
public class MainActivity extends DrawerBaseActivity implements View.OnClickListener, OnItemClickListener, View.OnTouchListener {


    //    view


    //    adapter


    //   变量
    //保存点击的时间
    private long exitTime;
    private MainFragment mMainFragment;
    //actionbar开关对象
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_nevigation;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        ActivityCollector.finishAllActivity();
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getIntent().getIntExtra("type", -1) != -1) {
            int type = getIntent().getIntExtra("type", -1);
            mMainFragment.changeText(type);
            selectNevigationText(type);
        }
    }

    @Override
    protected void initVariables() {
        super.initVariables();
        mMainFragment = new MainFragment();
    }

    @Override
    protected void initView() {
        super.initView();
    }


    @Override
    protected void initData() {
        super.initData();
        initDrawerToggle();
        selectNevigationText(DrawerBaseActivity.COURSE);
    }


    @Override
    protected void initListener() {
        super.initListener();
        mDrawerContainer.setDrawerListener(mDrawerToggle);


    }

    @Override
    protected void loadData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_main_drawerCourse:
                mDrawerContainer.closeDrawer(Gravity.LEFT);
                mMainFragment.changeText(DrawerBaseActivity.COURSE);
                selectNevigationText(DrawerBaseActivity.COURSE);
                return;

            case R.id.tv_main_drawerMessage:
                mDrawerContainer.closeDrawer(Gravity.LEFT);
                mMainFragment.changeText(DrawerBaseActivity.MESSAGE);
                selectNevigationText(DrawerBaseActivity.MESSAGE);
                return;
            default:
                break;
        }
        super.onClick(v);
    }


    @Override
    public void onItemClick(View view, int position) {
        super.onItemClick(view, position);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            mDrawerContainer.post(new Runnable() {
                @Override
                public void run() {
                    supportInvalidateOptionsMenu();
                }
            });
            return true;
        }

        switch (item.getItemId()) {
            case R.id.search:
                startActivity(new Intent(mContext, SearchActivity.class));

                break;

            case R.id.notify:
                startActivity(new Intent(mContext, NotificationActivity.class));
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //双击退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK && mDrawerContainer.isDrawerOpen(Gravity.LEFT)) {
            mDrawerContainer.closeDrawer(Gravity.LEFT);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                showToast("再按一次退出", 0);
                exitTime = System.currentTimeMillis();
                return true;
            } else {

                ActivityCollector.finishAllActivity();
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void initToolBar() {
        super.initToolBar();
        getSupportActionBar().setTitle("课堂");

    }

    @Override
    protected Fragment getLayoutFragment() {
        return mMainFragment;
    }

    private void selectNevigationText(int type) {
        if (type == DrawerBaseActivity.COURSE) {
            mDrawerCourseText.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
            mDrawerMessageText.setBackgroundColor(getResources().getColor(android.R.color.white));

        } else {
            mDrawerMessageText.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
            mDrawerCourseText.setBackgroundColor(getResources().getColor(android.R.color.white));

        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mDrawerToggle.onConfigurationChanged(newConfig);

    }

    private void initDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle((Activity) mContext, mDrawerContainer, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }


            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
                supportInvalidateOptionsMenu();

            }
        };
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isOpen = mDrawerContainer.isDrawerVisible(mfl_main_drawerContent);
        menu.findItem(R.id.search).setVisible(!isOpen);
        menu.findItem(R.id.notify).setVisible(!isOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
