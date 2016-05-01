package com.ketangpai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ketangpai.activity.THomeworkDetailActivity;
import com.ketangpai.adapter.THomeworkAdapter;
import com.ketangpai.base.BasePresenterFragment;
import com.ketangpai.bean.Student_Homework;
import com.ketangpai.bean.Teacher_Homework;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.presenter.THomeworkPresenter;
import com.ketangpai.viewInterface.THomeworkViewInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by nan on 2016/5/1.
 */
public class THomeworkFragment extends BasePresenterFragment<THomeworkViewInterface, THomeworkPresenter> implements THomeworkViewInterface, OnItemClickListener {


    public static final String TAG = "===THomeworkFragment";

    @InjectView(R.id.list_t_homework)
    RecyclerView listTHomework;

    //adapter
    private THomeworkAdapter mTHomeworkAdapter;

    //various
    private Teacher_Homework homework;
    private List<Student_Homework> mHomeworks;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_t_homework;
    }

    @Override
    protected void initVarious() {
        super.initVarious();
        homework = (Teacher_Homework) getActivity().getIntent().getSerializableExtra("homework");
    }

    @Override
    protected void initView() {
        super.initView();
        listTHomework.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mHomeworks = new ArrayList<>();
        mTHomeworkAdapter = new THomeworkAdapter(mContext, mHomeworks);
        listTHomework.setAdapter(mTHomeworkAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mTHomeworkAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {
        super.loadData();
        mPresenter.getStudentHomeworkList(mContext,homework.getH_id());
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(mContext, THomeworkDetailActivity.class);
        intent.putExtra("homework", mHomeworks.get(position));
        startActivity(intent);
    }


    @Override
    protected THomeworkPresenter createPresenter() {
        return new THomeworkPresenter();
    }

    @Override
    public void getStudentHomeworkListOnComplete(List<Student_Homework> homeworklist) {
        mHomeworks.addAll(homeworklist);
        Collections.reverse(mHomeworks);
        mTHomeworkAdapter.notifyDataSetChanged();
    }
}
