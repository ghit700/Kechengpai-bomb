package com.ketangpai.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.ketangpai.adapter.AddExamAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.bean.Teacher_Course;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.view.FullyLinearLayoutManager;
import com.shamanland.fab.ShowHideOnScroll;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nan on 2016/5/3.
 */
public class AddExamFragment extends BaseFragment implements View.OnClickListener {

    //view
    private FloatingActionButton btnExamJudge;
    private FloatingActionButton btnExamSingleSelection;
    private FloatingActionButton btnExamMultipleSelection;
    private FloatingActionButton btnExamShortAnswer;
    private FloatingActionsMenu btnExamAdd;
    private RecyclerView listExam;

    //adapter
    private AddExamAdapter mAddExamAdapter;

    //various
    private List mExams;
    private String mTitle;
    private String mContent;
    private Teacher_Course mCourse;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_addexam;
    }

    @Override
    protected void initVarious() {
        super.initVarious();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mContent = getActivity().getIntent().getStringExtra("content");
        mTitle = getActivity().getIntent().getStringExtra("title");
        mCourse = (Teacher_Course) getActivity().getIntent().getSerializableExtra("course");
    }

    @Override
    protected void initView() {
        btnExamJudge = (FloatingActionButton) view.findViewById(R.id.btn_exam_judge);
        btnExamSingleSelection = (FloatingActionButton) view.findViewById(R.id.btn_exam_single_selection);
        btnExamMultipleSelection = (FloatingActionButton) view.findViewById(R.id.btn_exam_multiple_selection);
        btnExamShortAnswer = (FloatingActionButton) view.findViewById(R.id.btn_exam_short_answer);
        btnExamAdd = (FloatingActionsMenu) view.findViewById(R.id.btn_exam_add);
        listExam = (RecyclerView) view.findViewById(R.id.list_exam);

        listExam.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mExams = new ArrayList();
        mAddExamAdapter = new AddExamAdapter(mContext, mExams);
        listExam.setAdapter(mAddExamAdapter);

    }

    @Override
    protected void initData() {
        for (int i = 0; i < 30; ++i) {
            mAddExamAdapter.addItem(i, "11");
        }
    }

    @Override
    protected void initListener() {
        btnExamJudge.setOnClickListener(this);
        btnExamSingleSelection.setOnClickListener(this);
        btnExamMultipleSelection.setOnClickListener(this);
        btnExamShortAnswer.setOnClickListener(this);
        listExam.setOnTouchListener(new ShowHideOnScroll(btnExamAdd));

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exam_judge:
                addJudgeSubject();
                break;
            case R.id.btn_exam_multiple_selection:
                addMultipleSelectionSubject();
                break;
            case R.id.btn_exam_single_selection:
                addSingleSelectionSubject();
                break;
            case R.id.btn_exam_short_answer:
                addShortAnswerSubject();
                break;

            default:
                break;
        }
    }

    private void addShortAnswerSubject() {
        btnExamAdd.collapse();
    }

    private void addSingleSelectionSubject() {
        btnExamAdd.collapse();

    }

    private void addMultipleSelectionSubject() {
        btnExamAdd.collapse();

    }

    private void addJudgeSubject() {
        btnExamAdd.collapse();

    }

    public void publishExam() {
    }


}
