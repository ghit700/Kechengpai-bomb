package com.ketangpai.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ketangpai.adapter.ExamAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.base.BasePresenterFragment;
import com.ketangpai.bean.Student_Reply;
import com.ketangpai.bean.Subject;
import com.ketangpai.bean.Test;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.presenter.TExamDetailPresenter;
import com.ketangpai.presenter.TExamPresenter;
import com.ketangpai.view.FullyLinearLayoutManager;
import com.ketangpai.viewInterface.TExamDetailViewInterface;
import com.ketangpai.viewInterface.TExamViewInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by nan on 2016/5/8.
 */
public class TExamDetailFragment extends BasePresenterFragment<TExamDetailViewInterface, TExamDetailPresenter> implements TExamDetailViewInterface {
    @InjectView(R.id.list_exam)
    RecyclerView listExam;
    @InjectView(R.id.btn_exam_submit)
    Button btnExamSubmit;

    //adapter
    ExamAdapter mExamAdapter;

    //various
    Student_Reply mStudent_reply;
    private List<Subject> mSubjects;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_t_detail_exam;
    }

    @Override
    protected void initVarious() {
        super.initVarious();
        mStudent_reply = (Student_Reply) getActivity().getIntent().getSerializableExtra("student_reply");
    }

    @Override
    protected void initView() {
        listExam.setLayoutManager(new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        if (null != mStudent_reply.getSubjects()) {
            mSubjects = mStudent_reply.getSubjects();
        } else {
            mSubjects = new ArrayList<>();
        }
        mExamAdapter = new ExamAdapter(mContext, mSubjects, 0);
        listExam.setAdapter(mExamAdapter);

        if (null != mStudent_reply.getGrade()) {
            btnExamSubmit.setText(String.valueOf(mStudent_reply.getGrade()));
            btnExamSubmit.setTextColor(mContext.getResources().getColor(R.color.actionsheet_red));
            btnExamSubmit.setBackgroundResource(R.color.white);
            btnExamSubmit.setText("分数:" + mStudent_reply.getGrade());
        }


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

    @OnClick(R.id.btn_exam_submit)
    public void onBtn_exam_submitClick() {
        if (btnExamSubmit.getText().equals("批改")) {
            mStudent_reply.setT_state("已批改");
            mPresenter.correctExam(mContext, mStudent_reply);
            btnExamSubmit.setText(String.valueOf(mStudent_reply.getGrade()));
            btnExamSubmit.setTextColor(mContext.getResources().getColor(R.color.actionsheet_red));
            btnExamSubmit.setBackgroundResource(R.color.white);
            btnExamSubmit.setText("分数:" + String.valueOf(mStudent_reply.getGrade()));
        }
    }


    @Override
    protected TExamDetailPresenter createPresenter() {
        return new TExamDetailPresenter();
    }
}
