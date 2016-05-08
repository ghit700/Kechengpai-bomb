package com.ketangpai.activity;

import android.support.v4.app.Fragment;

import com.ketangpai.base.BaseToolbarActivity;
import com.ketangpai.bean.Teacher_Homework;
import com.ketangpai.bean.Test;
import com.ketangpai.fragment.SExamFragment;
import com.ketangpai.fragment.TExamFragment;
import com.ketangpai.nan.ketangpai.R;

/**
 * Created by nan on 2016/5/8.
 */
public class ExamActivity extends BaseToolbarActivity {

    private Test test;
    private int type;

    @Override
    protected void initVariables() {
        super.initVariables();
        if (null != getIntent().getSerializableExtra("test")) {
            test = (Test) getIntent().getSerializableExtra("test");
        }
        type = getSharedPreferences("user", 0).getInt("type", -1);
    }

    @Override
    protected Fragment getLayoutFragment() {
        if (type == 0) {
            return new TExamFragment();
        } else {
            return new SExamFragment();
        }
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_course_add_tab;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setTitle(test.getTitle());
    }
}
