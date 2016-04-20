package com.ketangpai.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ketangpai.base.BaseActivity;
import com.ketangpai.base.BasePresenter;
import com.ketangpai.base.BaseToolbarActivity;
import com.ketangpai.fragment.AddHomeWorkFragment;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.IntentUtils;

import java.util.Calendar;

/**
 * Created by nan on 2016/3/27.
 */
public class AddHomeWorkActivity extends BaseToolbarActivity {

    private AddHomeWorkFragment mFragment;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_course_add_tab;
    }

    @Override
    protected Fragment getLayoutFragment() {
        mFragment = new AddHomeWorkFragment();
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
        super.loadData();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.send:
                mFragment.sendHomeWork();
                break;
            case R.id.data:
                IntentUtils.openDocument(mFragment);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_homework_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setTitle("新作业");
    }
}
