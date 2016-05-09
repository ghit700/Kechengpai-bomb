package com.ketangpai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ketangpai.base.BaseActivity;
import com.ketangpai.base.BaseToolbarActivity;
import com.ketangpai.bean.Installation;
import com.ketangpai.fragment.ContactsFragment;
import com.ketangpai.fragment.MainCourseFragment;
import com.ketangpai.fragment.MeFragment;
import com.ketangpai.fragment.MessageFragment;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.ActivityCollector;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.push.BmobPush;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by nan on 2016/3/9.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.tv_main_course)
    TextView tvMainCourse;
    @InjectView(R.id.tv_main_message)
    TextView tvMainMessage;
    @InjectView(R.id.tv_main_contacts)
    TextView tvMainContacts;
    @InjectView(R.id.tv_main_account)
    TextView tvMainAccount;


    //    view


    //    adapter


    //   变量
    //保存点击的时间
    private long exitTime;

    private String account;
    private Menu mMenu;
    private MainCourseFragment mMainCourseFragment;
    private ContactsFragment mContactsFragment;
    private MessageFragment mMessageFragment;
    private MeFragment mMeFragment;
    private boolean haveNotify;
    private Toolbar mToolbar;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_nevigation;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }


    ;


    @Override
    protected void initVariables() {
        super.initVariables();
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation(this).save();
        registerAccount();
        // 启动推送服务
        BmobPush.startWork(this);
        account = getSharedPreferences("user", 0).getString("account", "");
        mMainCourseFragment = new MainCourseFragment();
        mContactsFragment = new ContactsFragment();
        mMessageFragment = new MessageFragment();
        mMeFragment = new MeFragment();

    }

    @Override
    protected void initView() {
        initToolbar();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_main_container, mMainCourseFragment).commit();

    }

    @Override
    protected void initData() {


    }


    @Override
    protected void initListener() {


    }


    @Override
    protected void loadData() {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.notify:
                setNotifyOff();
                Intent intent = new Intent(mContext, NotificationActivity.class);
                startActivity(intent);
                break;

        }
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //双击退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK) {
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

    protected void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("课堂");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_toolbar_menu, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    private void registerAccount() {
        BmobQuery<Installation> bmobQuery = new BmobQuery<Installation>();
        bmobQuery.addWhereEqualTo("installationId", BmobInstallation.getCurrentInstallation(this).getInstallationId());
        bmobQuery.findObjects(this, new FindListener<Installation>() {
            @Override
            public void onSuccess(List<Installation> list) {
                if (null != list) {
                    Installation installation = list.get(0);
                    installation.setAccount(account);
                    installation.update(mContext);
                } else {
                    Log.i("===MainActivity", "registerAccount list null");

                }
            }

            @Override
            public void onError(int i, String s) {
                Log.i("===MainActivity", "registerAccount " + s);
            }
        });
    }

    public void setNotifyOn() {
        haveNotify = true;
        mMenu.findItem(R.id.notify).setIcon(R.drawable.ic_notifications_on);
    }

    public void setNotifyOff() {
        haveNotify = false;
        mMenu.findItem(R.id.notify).setIcon(R.drawable.ic_notifications);

    }


    @OnClick({R.id.tv_main_course, R.id.tv_main_message, R.id.tv_main_contacts, R.id.tv_main_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_main_course:
                tvMainCourse.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvMainMessage.setTextColor(getResources().getColor(R.color.black));
                tvMainContacts.setTextColor(getResources().getColor(R.color.black));
                tvMainAccount.setTextColor(getResources().getColor(R.color.black));
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_main_container, mMainCourseFragment).commit();
                getSupportActionBar().setTitle("课程");
                break;
            case R.id.tv_main_message:
                tvMainMessage.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvMainCourse.setTextColor(getResources().getColor(R.color.black));
                tvMainContacts.setTextColor(getResources().getColor(R.color.black));
                tvMainAccount.setTextColor(getResources().getColor(R.color.black));
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_main_container, mMessageFragment).commit();
                getSupportActionBar().setTitle("私信");
                break;
            case R.id.tv_main_contacts:
                tvMainContacts.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvMainMessage.setTextColor(getResources().getColor(R.color.black));
                tvMainCourse.setTextColor(getResources().getColor(R.color.black));
                tvMainAccount.setTextColor(getResources().getColor(R.color.black));
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_main_container, mContactsFragment).commit();
                getSupportActionBar().setTitle("通讯录");
                break;
            case R.id.tv_main_account:
                tvMainAccount.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvMainMessage.setTextColor(getResources().getColor(R.color.black));
                tvMainContacts.setTextColor(getResources().getColor(R.color.black));
                tvMainCourse.setTextColor(getResources().getColor(R.color.black));
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_main_container, mMeFragment).commit();
                getSupportActionBar().setTitle("我");
                break;
        }
    }
}
