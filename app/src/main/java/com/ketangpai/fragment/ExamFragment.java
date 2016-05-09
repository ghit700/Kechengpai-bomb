package com.ketangpai.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.ketangpai.adapter.ExamAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.bean.Student_Reply;
import com.ketangpai.bean.Subject;
import com.ketangpai.bean.Test;
import com.ketangpai.callback.ResultCallback;
import com.ketangpai.model.ExamModel;
import com.ketangpai.modelImpl.ExamModelImpl;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.view.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by nan on 2016/5/8.
 */
public class ExamFragment extends BaseFragment {
    @InjectView(R.id.list_exam)
    RecyclerView listExam;
    @InjectView(R.id.btn_exam_submit)
    Button btnExamSubmit;


    //adapter
    private ExamAdapter mExamAdapter;

    //various
    private List<Subject> mSubjects;
    private String account;
    private ExamModel mExamModel;
    private Student_Reply mStudent_reply;
    private Test test;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_exam;
    }


    @Override
    protected void initVarious() {
        super.initVarious();
        account = mContext.getSharedPreferences("user", 0).getString("account", "");
        test = (Test) getActivity().getIntent().getSerializableExtra("test");
    }

    @Override
    protected void initView() {
        listExam.setLayoutManager(new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mSubjects = new ArrayList<>();
        mExamAdapter = new ExamAdapter(mContext, mSubjects);
        listExam.setAdapter(mExamAdapter);
        btnExamSubmit.setEnabled(false);
    }

    @Override
    protected void initData() {
        mExamModel = new ExamModelImpl();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {
        mExamModel.getStudentExam(mContext, test, account, new ResultCallback() {
            @Override
            public void onSuccess(Object object) {

                mStudent_reply = (Student_Reply) object;
                mSubjects.addAll(mStudent_reply.getSubjects());
                mExamAdapter.notifyDataSetChanged();
                if (null != mStudent_reply.getGrade()) {
                    btnExamSubmit.setText(String.valueOf(mStudent_reply.getGrade()));
                    btnExamSubmit.setTextColor(mContext.getResources().getColor(R.color.actionsheet_red));
                    btnExamSubmit.setBackgroundResource(R.color.white);
                } else if (!mStudent_reply.getS_state().equals("未交")) {
                    btnExamSubmit.setText(String.valueOf(mStudent_reply.getGrade()));
                    btnExamSubmit.setTextColor(mContext.getResources().getColor(R.color.actionsheet_red));
                    btnExamSubmit.setBackgroundResource(R.color.white);
                    btnExamSubmit.setText("还未批改");
                }
                btnExamSubmit.setEnabled(true);
            }

            @Override
            public void onFailure(String e) {

            }
        });
    }


    @OnClick(R.id.btn_exam_submit)
    public void onBtnExamSubmitClick() {
        if (!btnExamSubmit.getText().equals("提交")) {

        } else {
            if (test.getE_time() < System.currentTimeMillis()) {
                mStudent_reply.setS_state("逾时未交");
            } else {
                mStudent_reply.setS_state("按时交");
            }
            mStudent_reply.setT_state("未批改");
            mStudent_reply.setCommit_time(System.currentTimeMillis());
            mExamModel.publishStudentExam(mContext, mStudent_reply);
            mExamAdapter.notifyDataSetChanged();
            btnExamSubmit.setText(String.valueOf(mStudent_reply.getGrade()));
            btnExamSubmit.setTextColor(mContext.getResources().getColor(R.color.actionsheet_red));
            btnExamSubmit.setBackgroundResource(R.color.white);
            if (null != mStudent_reply.getGrade()) {
                btnExamSubmit.setText("分数:" + String.valueOf(mStudent_reply.getGrade()));
            } else {
                btnExamSubmit.setText("还未批改");

            }


        }
    }
}
