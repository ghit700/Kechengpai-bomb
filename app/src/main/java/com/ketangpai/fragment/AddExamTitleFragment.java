package com.ketangpai.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ketangpai.activity.AddExamActivity;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.bean.Teacher_Course;
import com.ketangpai.nan.ketangpai.R;

/**
 * Created by nan on 2016/5/4.
 */
public class AddExamTitleFragment extends BaseFragment implements View.OnClickListener {
    private Button btnExamSumit;
    private EditText etExamTitle;
    private EditText etExamContent;
    private Teacher_Course course;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_addexam_title;
    }


    @Override
    protected void initVarious() {
        super.initVarious();
        course = (Teacher_Course) getActivity().getIntent().getSerializableExtra("course");
    }

    @Override
    protected void initView() {
        btnExamSumit = (Button) view.findViewById(R.id.btn_exam_submit);
        etExamTitle = (EditText) view.findViewById(R.id.et_exam_title);
        etExamContent = (EditText) view.findViewById(R.id.et_exam_content);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        btnExamSumit.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        if (!etExamTitle.getText().toString().equals("")) {
            Intent intent = new Intent(mContext, AddExamActivity.class);
            intent.putExtra("course", course);
            intent.putExtra("title", etExamTitle.getText().toString());
            intent.putExtra("content", etExamContent.getText().toString());
            startActivity(intent);
        } else {
            sendToast("请输入测试名称");
        }
    }
}
