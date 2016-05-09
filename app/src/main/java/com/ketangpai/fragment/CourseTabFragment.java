package com.ketangpai.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ketangpai.activity.DataActivity;
import com.ketangpai.activity.ExamActivity;
import com.ketangpai.activity.NoticeActivity;
import com.ketangpai.activity.HomeWorkActivity;
import com.ketangpai.adapter.CourseDataAdapter;
import com.ketangpai.adapter.CourseNoticeAdapter;
import com.ketangpai.adapter.CourseExamAdapter;
import com.ketangpai.adapter.CourseHomeworkAdapter;
import com.ketangpai.base.BaseAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.bean.Course;
import com.ketangpai.bean.Data;
import com.ketangpai.bean.Notice;
import com.ketangpai.bean.Student_Course;
import com.ketangpai.bean.Teacher_Homework;
import com.ketangpai.bean.Test;
import com.ketangpai.callback.ResultsCallback;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.modelImpl.DataModelImpl;
import com.ketangpai.modelImpl.ExamModelImpl;
import com.ketangpai.modelImpl.HomeworkModelImpl;
import com.ketangpai.modelImpl.NoticeModelImpl;
import com.ketangpai.nan.ketangpai.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by nan on 2016/3/20.
 */
public class CourseTabFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {

    RecyclerView mTabList;
    SwipeRefreshLayout mSwipeRefreshLayout;

    //adapter
    private BaseAdapter mTabAdapter;

    //变量
    private List mTabContents;
    private int mPosition;
    private int c_id;
    private String c_name;
    private Course course;
    private HomeworkModelImpl homeworkModel;
    private DataModelImpl dataModel;
    private NoticeModelImpl noticeModel;
    private ExamModelImpl examModel;

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    private CourseTabFragment getInstance() {
        return this;
    }

    @Override
    protected void initVarious() {
        super.initVarious();
        c_id = course.getC_id();
        c_name = course.getName();
        homeworkModel = new HomeworkModelImpl();
        dataModel = new DataModelImpl();
        noticeModel = new NoticeModelImpl();
        examModel = new ExamModelImpl();
    }


    public void setPosition(int position) {
        this.mPosition = position;
    }

    public static CourseTabFragment newInstance(int positon, Course course) {
        CourseTabFragment fragment = new CourseTabFragment();
        fragment.setPosition(positon);
        fragment.setCourse(course);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_course_tab;
    }


    @Override
    protected void initView() {
        mTabList = (RecyclerView) view.findViewById(R.id.list_course_tab);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fresh_course_tab);
        initTabList();
    }

    @Override
    protected void initData() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        onRefresh();
    }

    @Override
    protected void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mTabAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void loadData() {

    }


    private void initTabList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mTabList.setLayoutManager(linearLayoutManager);
        mTabContents = new ArrayList();
        changeTabAdaterByPosition(mPosition, mContext);
    }

    public void changeTabAdaterByPosition(int position, Context context) {
        switch (position) {
            case 0:
                mTabAdapter = new CourseHomeworkAdapter(context, mTabContents);

                break;
            case 1:
                mTabAdapter = new CourseDataAdapter(context, mTabContents);


                break;
            case 2:
                mTabAdapter = new CourseNoticeAdapter(context, mTabContents);


                break;
            case 3:
                mTabAdapter = new CourseExamAdapter(context, mTabContents);

                break;

            default:
                break;
        }
        mTabList.setAdapter(mTabAdapter);

    }


    @Override
    public void onRefresh() {
        switch (mPosition) {
            case 0:
                getHomeworkListToStudent(c_id, ((Student_Course) course).getAdd_time());
                break;
            case 1:
                getDataList(c_id);
                break;
            case 2:
                getNoticeListToStudent((Student_Course) course);
                break;
            case 3:
                getExamListToStudent(c_id, ((Student_Course) course).getAdd_time());
                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent;
        switch (mPosition) {

            case 0:
                intent = new Intent(mContext, HomeWorkActivity.class);
                intent.putExtra("homework", (Teacher_Homework) mTabContents.get(position));
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(mContext, DataActivity.class);
                intent.putExtra("name", ((Data) mTabContents.get(position)).getName());
                intent.putExtra("url", ((Data) mTabContents.get(position)).getUrl());
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(mContext, NoticeActivity.class);
                intent.putExtra("notice", (Notice) mTabContents.get(position));
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(mContext, ExamActivity.class);
                intent.putExtra("test", (Test) mTabContents.get(position));
                startActivity(intent);
                break;

        }
    }


    public void getNoticeListToStudent(Student_Course course) {

        noticeModel.getNoticeListToStudent(mContext, course.getC_id(), course.getAdd_time(), new ResultsCallback() {
            @Override
            public void onSuccess(List list) {
                mSwipeRefreshLayout.setRefreshing(false);
                mTabContents.clear();
                Collections.reverse(list);
                mTabContents.addAll(list);
                mTabAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(BmobException e) {

            }
        });

    }


    public void getDataList(int c_id) {

        dataModel.getDataList(mContext, c_id, new ResultsCallback() {
            @Override
            public void onSuccess(List list) {
                mSwipeRefreshLayout.setRefreshing(false);
                mTabContents.clear();
                Collections.reverse(list);
                mTabContents.addAll(list);
                mTabAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(BmobException e) {
            }
        });

    }


    public void getHomeworkListToStudent(int c_id, long add_time) {

        homeworkModel.getHomeworkListToStudent(mContext, c_id, add_time, new ResultsCallback() {
            @Override
            public void onSuccess(List list) {
                mSwipeRefreshLayout.setRefreshing(false);
                mTabContents.clear();
                Collections.reverse(list);
                mTabContents.addAll(list);
                mTabAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(BmobException e) {
            }
        });

    }


    public void getExamListToStudent(int c_id, long add_time) {

        examModel.getExamListToStudent(mContext, c_id, add_time, new ResultsCallback() {
            @Override
            public void onSuccess(List list) {
                mSwipeRefreshLayout.setRefreshing(false);
                mTabContents.clear();
                Collections.reverse(list);
                mTabContents.addAll(list);
                mTabAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(BmobException e) {
            }
        });

    }


    public void setCourse(Course course) {
        this.course = course;
    }

}
