package com.ketangpai.base;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ketangpai.activity.AccountActivity;
import com.ketangpai.activity.CourseActivity;
import com.ketangpai.activity.LoginActivity;
import com.ketangpai.activity.MainActivity;
import com.ketangpai.activity.SearchActivity;
import com.ketangpai.adapter.NevigationCourseAdapter;
import com.ketangpai.adapter.NotificationAdapter;
import com.ketangpai.bean.Course;
import com.ketangpai.fragment.CourseFragment;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.nan.ketangpai.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by nan on 2016/3/15.
 */
public abstract class DrawerBaseActivity extends BaseActivity implements View.OnClickListener, OnItemClickListener, View.OnTouchListener {

    //导航界面
    protected FrameLayout mfl_main_drawerContent;
    protected DrawerLayout mDrawerContainer;
    //view
    protected Toolbar mToolbar;
    protected RecyclerView mDrawerCourseList;
    protected ImageView mExitLoginImg;
    private CircleImageView mUserIconImg;
    protected TextView mDrawerCourseText, mDrawerMessageText, mUserNameText;

    //adapter
    protected NevigationCourseAdapter mNevigationCourseAdapter;

    //变量
    //courselist
    protected List<String> mNevigationCourses;
    protected String name;

    //主界面打开的类型
    public final static int COURSE = 1;
    public final static int MESSAGE = 2;
    //notificationlist
    protected List mNotificationContents;


    @Override
    protected void onStop() {
        super.onStop();
        mDrawerContainer.closeDrawer(Gravity.LEFT);
    }

    @Override
    protected void initVariables() {
        super.initVariables();
        name = getSharedPreferences("user", 0).getString("name", "");
    }

    @Override
    protected void initView() {
        initToolBar();
        initDrawerContent();
        mUserIconImg = (CircleImageView) findViewById(R.id.img_main_userIcon);
        mExitLoginImg = (ImageView) findViewById(R.id.img_main_exitLogin);
        mfl_main_drawerContent = (FrameLayout) findViewById(R.id.fl_main_drawerContent);
        mDrawerContainer = (DrawerLayout) findViewById(R.id.drawer_main_container);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_main_container, getLayoutFragment()).commit();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mDrawerMessageText.setOnClickListener(this);
        mDrawerCourseText.setOnClickListener(this);
        mNevigationCourseAdapter.setOnItemClickListener(this);
        mExitLoginImg.setOnClickListener(this);
        mUserIconImg.setOnClickListener(this);
        //拦截点击事件，不让在导航界面的点击事件传递到mainContent中
        mfl_main_drawerContent.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_main_drawerCourse:
                startMainActivity(COURSE);
                finish();
                break;
            case R.id.tv_main_drawerMessage:
                startMainActivity(MESSAGE);
                finish();
                break;
            case R.id.img_main_exitLogin:
                showExitDialog();
                break;
            case R.id.img_main_userIcon:
                startAccountActivity();
                break;
        }


    }


    @Override
    public void onItemClick(View view, int position) {
        startCourseActivity(mNevigationCourses.get(position), position);

    }


    private void startCourseActivity(String course, int position) {
        Intent intent = new Intent(mContext, CourseActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("course", course);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mDrawerContainer.isDrawerOpen(Gravity.LEFT)) {
            mDrawerContainer.closeDrawer(Gravity.LEFT);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void initDrawerContent() {
        initDrawerCourseList();
        mUserIconImg = (CircleImageView) findViewById(R.id.img_main_userIcon);
        mExitLoginImg = (ImageView) findViewById(R.id.img_main_exitLogin);
        mUserNameText = (TextView) findViewById(R.id.tv_main_userName);
        mDrawerCourseText = (TextView) findViewById(R.id.tv_main_drawerCourse);
        mDrawerMessageText = (TextView) findViewById(R.id.tv_main_drawerMessage);

        mUserNameText.setText(name);

    }


    protected void initDrawerCourseList() {
        mDrawerCourseList = (RecyclerView) findViewById(R.id.list_main_drawer_course);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mDrawerCourseList.setLayoutManager(linearLayoutManager);
        mNevigationCourses = new ArrayList<>();
        mNevigationCourseAdapter = new NevigationCourseAdapter(mContext, mNevigationCourses);
        mDrawerCourseList.setAdapter(mNevigationCourseAdapter);
    }


    protected void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    protected abstract Fragment getLayoutFragment();


    private void startMainActivity(int type) {
        Intent intent = new Intent(mContext, MainActivity.class);
        if (type == COURSE) {
            intent.putExtra("type", COURSE);
        } else {
            intent.putExtra("type", MESSAGE);
        }
        startActivity(intent);
    }

    ;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    protected void showExitDialog() {
        new AlertDialog.Builder(mContext).setTitle("退出登录").setMessage("是否确认退出登录").setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getSharedPreferences("user", 0).edit().clear().commit();
                startActivity(new Intent(mContext, LoginActivity.class));
                finish();
            }
        }).create().show();
    }

    ;

    protected void startAccountActivity() {

        Intent intent = new Intent(mContext, AccountActivity.class);
        startActivity(intent);
    }


    public NevigationCourseAdapter getNevigationCourseAdapter() {
        return mNevigationCourseAdapter;
    }
}
