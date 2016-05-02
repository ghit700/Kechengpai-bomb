package com.ketangpai.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ketangpai.activity.AddHomeWorkActivity;
import com.ketangpai.activity.AddNoticekActivity;
import com.ketangpai.activity.DataActivity;
import com.ketangpai.activity.NoticeActivity;
import com.ketangpai.activity.HomeWorkActivity;
import com.ketangpai.adapter.CourseDataAdapter;
import com.ketangpai.adapter.CourseNoticeAdapter;
import com.ketangpai.adapter.CourseTExamAdapter;
import com.ketangpai.adapter.CourseHomeworkAdapter;
import com.ketangpai.base.BaseAdapter;
import com.ketangpai.base.BasePresenterFragment;
import com.ketangpai.bean.Course;
import com.ketangpai.bean.Data;
import com.ketangpai.bean.Notice;
import com.ketangpai.bean.Student_Course;
import com.ketangpai.bean.Teacher_Homework;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.presenter.CourseTabPresenter;
import com.ketangpai.utils.FileUtils;
import com.ketangpai.utils.IntentUtils;
import com.ketangpai.viewInterface.CourseTabViewInterface;
import com.shamanland.fab.ShowHideOnScroll;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nan on 2016/3/20.
 */
public class CourseTabFragment extends BasePresenterFragment<CourseTabViewInterface, CourseTabPresenter> implements SwipeRefreshLayout.OnRefreshListener, CourseTabViewInterface, OnItemClickListener, View.OnClickListener {
    public static final String TAG = "===CourseTabFragment";
    //view
    FloatingActionButton mPublishBtn;
    RecyclerView mTabList;
    SwipeRefreshLayout mSwipeRefreshLayout;

    //adapter
    private BaseAdapter mTabAdapter;

    //变量
    private List mTabContents;
    private int mPosition;
    private int type;
    private final int REQUEST = 11;
    private int c_id;
    private String c_name;
    private Course course;
    private ProgressDialog mUploadDialog;

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
        type = mContext.getSharedPreferences("user", 0).getInt("type", -1);
        c_id = course.getC_id();
        c_name = course.getName();
    }

    @Override
    public void onStop() {
        super.onStop();

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
        if (type == 0) {
            mPublishBtn = (FloatingActionButton) view.findViewById(R.id.btn_course_tab_publish);
            mPublishBtn.setOnClickListener(this);
            mPublishBtn.setVisibility(View.VISIBLE);
            mTabList.setOnTouchListener(new ShowHideOnScroll(mPublishBtn));
        }
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fresh_course_tab);
        initTabList();
    }

    @Override
    protected void initData() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setRefreshing(true);
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
                mTabAdapter = new CourseHomeworkAdapter(context, mTabContents, type);
                if (type == 0) {
                    mPublishBtn.setImageResource(R.drawable.category_1004);
                } else {
                    mPresenter.getHomeworkListToStudent(context, c_id, ((Student_Course) course).getAdd_time());
                }
                break;
            case 1:
                mTabAdapter = new CourseDataAdapter(context, mTabContents);
                mPresenter.getDataList(context, c_id);
                if (type == 0) {
                    mPublishBtn.setImageResource(R.drawable.category_1002);
                }

                break;
            case 2:
                mTabAdapter = new CourseNoticeAdapter(context, mTabContents, type);
                if (type == 0) {
                    mPresenter.getNoticeList(context, c_id);
                    mPublishBtn.setImageResource(R.drawable.category_1003);
                } else {
                    mPresenter.getNoticeListToStudent(mContext, (Student_Course) course);
                }

                break;
            case 3:
                mTabAdapter = new CourseTExamAdapter(context, mTabContents, type);
                if (type == 0) {
                    mPublishBtn.setImageResource(R.drawable.category_1001);
                }

                break;

            default:
                break;
        }
        mTabList.setAdapter(mTabAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        switch (mPosition) {
            case 0:
                if (type == 0) {
                    mPresenter.getHomeworkList(mContext, c_id);
                }
                break;

            case 3:
                if (type == 0) {
                }

                break;

            default:
                break;
        }
    }

    @Override
    public void onRefresh() {

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
                break;
            case 4:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_course_tab_publish) {
            Intent intent;
            switch (mPosition) {
                case 0:
                    intent = new Intent(mContext, AddHomeWorkActivity.class);
                    intent.putExtra("course", course);
                    startActivityForResult(intent, REQUEST);
                    break;
                case 1:
                    IntentUtils.openDocument(getInstance());
                    break;
                case 2:
                    intent = new Intent(mContext, AddNoticekActivity.class);
                    intent.putExtra("c_id", c_id);
                    intent.putExtra("c_name", c_name);
                    startActivityForResult(intent, REQUEST);
                    break;
                case 3:
                    break;

                default:
                    break;
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //
        if (requestCode == IntentUtils.OPEN_DOCUMENT_REQUEST && resultCode == getActivity().RESULT_OK) {
            Uri uri = data.getData();
            File file = FileUtils.getFileByUri(getActivity(), uri);
            Data data1 = new Data();
            data1.setC_id(c_id);
            data1.setName(file.getName());
            data1.setSize(FileUtils.getFileSize(file.length()));
            data1.setUrl(file.getAbsolutePath());
            mTabAdapter.addItem(0, data1);
            mPresenter.uploadData(mContext, data1, c_id, c_name);
            showUploadDialog();
        }

        if (requestCode == REQUEST && resultCode == AddNoticeFragment.RESULT) {
            Notice notice = (Notice) data.getSerializableExtra("notice");
            mTabAdapter.addItem(0, notice);
            mTabList.smoothScrollToPosition(0);
        }
        if (requestCode == REQUEST && resultCode == AddHomeworkFragment.RESULT) {
            Teacher_Homework homework = (Teacher_Homework) data.getSerializableExtra("homework");
            mTabAdapter.addItem(0, homework);
            mTabList.smoothScrollToPosition(0);
        }


    }

    private void showUploadDialog() {

        mUploadDialog = new ProgressDialog(mContext);
        mUploadDialog.setTitle("文件上传");
        mUploadDialog.setMax(100);
        mUploadDialog.setMessage("文件上传完成百分比");
        mUploadDialog.setCancelable(false);
        mUploadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mUploadDialog.setIndeterminate(false);
        mUploadDialog.show();
    }

    @Override
    public void getHomeworkListOnComplete(List<Teacher_Homework> homeworks) {
        mTabContents.clear();
        Collections.reverse(homeworks);
        mTabContents.addAll(homeworks);
        mTabAdapter.notifyDataSetChanged();
    }

    @Override
    public void uploadOnProgress(int value) {
        mUploadDialog.setProgress(value);
    }

    @Override
    public void uploadDataOnComplete(String url) {
        mUploadDialog.dismiss();
        ((Data) mTabContents.get(0)).setUrl(url);
        ((Data) mTabContents.get(0)).update(mContext);
        mTabAdapter.updateItem(0);
    }

    @Override
    public void getDataListOnComplete(List datas) {
        Collections.reverse(datas);
        mTabContents.addAll(datas);
        mTabAdapter.notifyDataSetChanged();

    }

    @Override
    public void getNoticeListOnComplete(List<Notice> notices) {
        Collections.reverse(notices);

        mTabContents.addAll(notices);
        mTabAdapter.notifyDataSetChanged();
    }

    @Override
    public void getExamkListOnComplete(List exams) {
        Collections.reverse(exams);

        mTabContents.addAll(exams);
        mTabAdapter.notifyDataSetChanged();
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    protected CourseTabPresenter createPresenter() {
        return new CourseTabPresenter();
    }
}
