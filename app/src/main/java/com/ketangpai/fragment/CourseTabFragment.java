package com.ketangpai.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.ketangpai.activity.AddHomeWorkActivity;
import com.ketangpai.activity.AddNoticekActivity;
import com.ketangpai.activity.DataActivity;
import com.ketangpai.activity.NoticeActivity;
import com.ketangpai.adapter.CourseDataAdapter;
import com.ketangpai.adapter.CourseNoticeAdapter;
import com.ketangpai.adapter.CourseTExamAdapter;
import com.ketangpai.adapter.CourseTHomeworkAdapter;
import com.ketangpai.base.BaseAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.bean.DocumentFile;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.FileUtils;
import com.ketangpai.utils.IntentUtils;
import com.shamanland.fab.ShowHideOnScroll;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nan on 2016/3/20.
 */
public class CourseTabFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnItemClickListener, View.OnClickListener {

    //view
    FloatingActionButton mPublishBtn;
    RecyclerView mTabList;
    SwipeRefreshLayout mSwipeRefreshLayout;

    //adapter
    private BaseAdapter mTabAdapter;

    //变量
    private List mTabContents;
    private int mPosition;
    private Animation mAddCloseAnim, mAddOpenAnim;

    private CourseTabFragment getInstance() {
        return this;
    }

    @Override
    public void onStop() {
        super.onStop();
        mPublishBtn.startAnimation(mAddCloseAnim);
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    public static CourseTabFragment newInstance(int positon) {
        CourseTabFragment fragment = new CourseTabFragment();
        fragment.setPosition(positon);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_course_tab;
    }

    @Override
    protected void initView() {
        mPublishBtn = (FloatingActionButton) view.findViewById(R.id.btn_course_tab_publish);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fresh_course_tab);
        mTabList = (RecyclerView) view.findViewById(R.id.list_course_tab);
        initTabList();
    }

    @Override
    protected void initData() {
        initAnim();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    protected void initListener() {

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mTabAdapter.setOnItemClickListener(this);
        mPublishBtn.setOnClickListener(this);
        mTabList.setOnTouchListener(new ShowHideOnScroll(mPublishBtn));
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
                mTabAdapter = new CourseTHomeworkAdapter(context, mTabContents);
                break;
            case 1:
                mTabAdapter = new CourseDataAdapter(context, mTabContents);
                break;
            case 2:
                mTabAdapter = new CourseNoticeAdapter(context, mTabContents);
                for (int i = 0; i < 10; ++i) {
                    mTabAdapter.addItem(i, "111");
                }
                break;
            case 3:
                mTabAdapter = new CourseTExamAdapter(context, mTabContents);
                break;

            default:
                break;
        }
        mTabList.setAdapter(mTabAdapter);

    }

    private void initAnim() {
        mAddCloseAnim = AnimationUtils.loadAnimation(mContext, R.anim.fab_rotate_close);
        mAddOpenAnim = AnimationUtils.loadAnimation(mContext, R.anim.fab_rotate_open);

        mAddOpenAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                switch (mPosition) {
                    case 0:
                        startActivity(new Intent(mContext, AddHomeWorkActivity.class));
                        break;
                    case 1:
                        IntentUtils.openDocument(getInstance());
                        break;
                    case 2:
                        startActivity(new Intent(mContext, AddNoticekActivity.class));
                        break;
                    case 3:
                        break;

                    default:
                        break;
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemClick(View view, int position) {
        switch (mPosition) {

            case 0:
                break;
            case 1:
                startActivity(new Intent(mContext, DataActivity.class));
                break;
            case 2:
                startActivity(new Intent(mContext, NoticeActivity.class));
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
            mPublishBtn.startAnimation(mAddOpenAnim);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //
        if (requestCode == IntentUtils.OPEN_DOCUMENT_REQUEST && resultCode == getActivity().RESULT_OK) {
            Uri uri = data.getData();
            String fileName = FileUtils.getFileName(uri);
            int fileType = FileUtils.getFileType(fileName);
            String size = FileUtils.getFileSize(uri);
            DocumentFile documentFile = new DocumentFile(fileType, fileName, size);
            documentFile.setPath(uri.getPath());
            mTabAdapter.addItem(mTabContents.size(), documentFile);
        }

        mPublishBtn.startAnimation(mAddCloseAnim);

    }
}
