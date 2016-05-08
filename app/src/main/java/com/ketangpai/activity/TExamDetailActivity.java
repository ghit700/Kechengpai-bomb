package com.ketangpai.activity;

import android.support.v4.app.Fragment;

import com.ketangpai.base.BaseToolbarActivity;
import com.ketangpai.bean.Student_Homework;
import com.ketangpai.bean.Student_Reply;
import com.ketangpai.fragment.TExamDetailFragment;
import com.ketangpai.fragment.THomeworkDetailFragment;
import com.ketangpai.nan.ketangpai.R;

/**
 * Created by nan on 2016/5/1.
 */
public class TExamDetailActivity extends BaseToolbarActivity {


    private Student_Reply student_reply;

    @Override
    protected void initVariables() {
        super.initVariables();
        student_reply = (Student_Reply) getIntent().getSerializableExtra("student_reply");
    }

    @Override
    protected Fragment getLayoutFragment() {
        return new TExamDetailFragment();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_course_add_tab;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        getSupportActionBar().setTitle(student_reply.getStudent_name());
    }
}
