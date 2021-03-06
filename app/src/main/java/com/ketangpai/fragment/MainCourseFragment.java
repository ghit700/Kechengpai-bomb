package com.ketangpai.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ketangpai.activity.CourseActivity;
import com.ketangpai.activity.MainActivity;
import com.ketangpai.adapter.CourseSMainCourseAdapter;
import com.ketangpai.adapter.CourseTMainCourseAdapter;
import com.ketangpai.adapter.NevigationCourseAdapter;
import com.ketangpai.base.BaseAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.base.BasePresenter;
import com.ketangpai.base.BasePresenterFragment;
import com.ketangpai.base.DrawerBaseActivity;
import com.ketangpai.bean.Course;
import com.ketangpai.bean.Student_Course;
import com.ketangpai.bean.Teacher_Course;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.presenter.MainCoursePresenter;
import com.ketangpai.utils.CodeUtils;
import com.ketangpai.viewInterface.MainCourseViewInterface;
import com.shamanland.fab.FloatingActionButton;
import com.shamanland.fab.ShowHideOnScroll;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by nan on 2016/3/15.
 */
public class MainCourseFragment extends BasePresenterFragment<MainCourseViewInterface, MainCoursePresenter> implements MainCourseViewInterface, View.OnClickListener, OnItemClickListener, DialogInterface.OnDismissListener, SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "===MainCourseFragment";
    //view
    private FloatingActionButton mAddBtn;
    private RecyclerView mMainCourseList;
    private AlertDialog mAddDialog;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    //adapter
    private BaseAdapter mMainCourseAdapter;

    //addBtn的动画
    private Animation mAddOpenAnim;
    private Animation mAddCloseAnim;

    //变量
    //course数组
    private List mCourses;
    //判断addBtn是否open
    private boolean isBtnOpen = true;
    //判断是老师还是学生
    private int type;
    private String account;
    private boolean First = true;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_course;
    }


    @Override
    protected void initVarious() {
        super.initVarious();
        type = mContext.getSharedPreferences("user", 0).getInt("type", -1);
        account = mContext.getSharedPreferences("user", 0).getString("account", "");
        initAddBtnAnim();
        if (type == 0) {
            mCourses = new ArrayList<Teacher_Course>();
            mMainCourseAdapter = new CourseTMainCourseAdapter(mContext, mCourses);
        } else {
            mCourses = new ArrayList<Student_Course>();
            mMainCourseAdapter = new CourseSMainCourseAdapter(mContext, mCourses);
        }

    }

    @Override
    protected void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fresh_main_course);
        mAddBtn = (FloatingActionButton) view.findViewById(R.id.btn_main_add);
        ininMainCourseList(view);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mAddBtn.setOnClickListener(this);
        mMainCourseList.setOnTouchListener(new ShowHideOnScroll(mAddBtn));
        mMainCourseAdapter.setOnItemClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void loadData() {
        Log.i(TAG, First + "");
        if (First) {
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            });
            onRefresh();
            First = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_add:
                changeAddBtnAnim();
                showAddDialog();
                break;


            default:
                break;
        }

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(mContext, CourseActivity.class);
        intent.putExtra("course", ((Course) mCourses.get(position)).getName());
        intent.putExtra("c_id", ((Course) mCourses.get(position)).getC_id());
        startActivity(intent);
    }

    private void showAddDialog() {
        mAddDialog = new AlertDialog.Builder(mContext).create();
        mAddDialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_add, null);
        TextView dialogTitle = (TextView) view.findViewById(R.id.tv_addDialog_title);
        final EditText dialogCourse = (EditText) view.findViewById(R.id.et_addDialog_courseName);
        Button btnCancel = (Button) view.findViewById(R.id.btn_addDialog_cancel);
        Button btnCreate = (Button) view.findViewById(R.id.btn_addDialog_create);

        //设置事件监听
        btnCancel.setOnClickListener(this);
        btnCreate.setOnClickListener(this);
        mAddDialog.setOnDismissListener(this);

        if (type == 0) {
            dialogTitle.setText("新建班级");
            dialogCourse.setHint("请输入新建班级名称");
            btnCreate.setText("创建");
        } else {
            dialogTitle.setText("加入班级");
            dialogCourse.setHint("请输入班级邀请码");
            btnCreate.setText("加入");
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddDialog.dismiss();
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCourse(dialogCourse.getText().toString());
            }
        });


        mAddDialog.setView(view);
        mAddDialog.show();
    }

    private void createCourse(String class_name) {
        if (type == 0) {
            Teacher_Course teacher_course = new Teacher_Course();
            teacher_course.setCode(CodeUtils.createCode());
            teacher_course.setAccount(account);
            teacher_course.setNumbers(0);
            teacher_course.setT_name(mContext.getSharedPreferences("user", 0).getString("name", ""));
            teacher_course.setName(class_name);
            mPresenter.createCourse(mContext, teacher_course);
        } else {
            mPresenter.addCourse(mContext, class_name, account);
        }
    }


    private void ininMainCourseList(View view) {
        mMainCourseList = (RecyclerView) view.findViewById(R.id.list_main_course);
        mMainCourseList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mMainCourseList.setAdapter(mMainCourseAdapter);

    }

    //addAnim
    private void initAddBtnAnim() {
        mAddCloseAnim = AnimationUtils.loadAnimation(mContext, R.anim.fab_rotate_close);
        mAddOpenAnim = AnimationUtils.loadAnimation(mContext, R.anim.fab_rotate_open);
    }

    //AddBtn open or close时设置动画
    private void changeAddBtnAnim() {

        if (isBtnOpen) {
            mAddBtn.startAnimation(mAddOpenAnim);
            isBtnOpen = false;
        } else {
            mAddBtn.startAnimation(mAddCloseAnim);
            isBtnOpen = true;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        changeAddBtnAnim();
    }

    @Override
    public void onRefresh() {
        mMainCourseAdapter.clearData();
        mPresenter.getCourseList(mContext, account, type);
    }

    @Override
    public void getCourseListOnComplete(List<Course> courses) {
        if (null != courses) {
            Collections.reverse(courses);
            int start = 0;
            start = mCourses.size();
            mCourses.addAll(courses);
            Log.i(TAG, "getCourseListOnComplete===start=" + start + "  end=" + mCourses.size());
            if (start == 0) {
                mMainCourseAdapter.notifyDataSetChanged();
            } else {
                mMainCourseAdapter.notifyItemRangeInserted(start, mCourses.size());
            }

            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);

        }
    }

    @Override
    public void createCourseOnComplete(Teacher_Course course) {
        if (null != course) {
            mMainCourseAdapter.addItem(0, course);
            mMainCourseList.smoothScrollToPosition(0);
            sendToast("创建班级成功");

        } else {
            new AlertDialog.Builder(mContext).setTitle("创建班级失败")
                    .setPositiveButton("确认", null)
                    .create().show();
        }
        mAddDialog.dismiss();

    }

    @Override
    public void addCourseOnComplete(Student_Course course) {
        if (null != course) {
            Log.i(TAG, "addCourseOnComplete");
            mMainCourseAdapter.addItem(0, course);
            mMainCourseList.smoothScrollToPosition(0);
            sendToast("加入班级成功");
        } else {
            new AlertDialog.Builder(mContext).setTitle("加入班级失败")
                    .setPositiveButton("确认", null)
                    .create().show();
        }
        mAddDialog.dismiss();
    }

    @Override
    public void showLoading(int typte) {
        showLoadingDialog();
        if (typte == 0) {
            setLoadingText("创建班级中...");
        } else {
            setLoadingText("加入班级中...");
        }
    }

    @Override
    public void hideLoading() {
        dismissLoadingDialog();
    }


    @Override
    protected MainCoursePresenter createPresenter() {
        return new MainCoursePresenter();
    }
}
