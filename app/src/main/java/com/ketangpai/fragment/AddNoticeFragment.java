package com.ketangpai.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ketangpai.activity.AddNoticekActivity;
import com.ketangpai.adapter.DataAdapter;
import com.ketangpai.base.BaseAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.base.BasePresenterFragment;
import com.ketangpai.bean.DataFile;
import com.ketangpai.bean.DocumentFile;
import com.ketangpai.bean.Notice;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.presenter.AddNoticePresenter;
import com.ketangpai.utils.FileUtils;
import com.ketangpai.utils.IntentUtils;
import com.ketangpai.utils.TimeUtils;
import com.ketangpai.view.FullyLinearLayoutManager;
import com.ketangpai.viewInterface.AddNoticeViewInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nan on 2016/3/27.
 */
public class AddNoticeFragment extends BasePresenterFragment<AddNoticeViewInterface, AddNoticePresenter> implements View.OnClickListener, AddNoticeViewInterface {

    public static final String TAG = "===AddNoticeFragment";

    //view
    EditText etNoticeTitle;
    EditText etAddNoticeContent;

    RecyclerView listAddNoticeData;

    //adapter
    DataAdapter mAddNoticeDataAdapter;

    //变量
    List mDataList;
    StringBuffer paths;
    TextView tv_data_progress;
    ProgressBar pb_data;
    public static final int RESULT = 12;

    @Override
    protected void initVarious() {
        super.initVarious();
        paths = new StringBuffer();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_addnotice;
    }

    protected void initView() {
        etNoticeTitle = (EditText) view.findViewById(R.id.et_notice_title);
        etAddNoticeContent = (EditText) view.findViewById(R.id.et_add_notice_content);
        initDataList();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_notice_title:
                break;
            case R.id.et_add_notice_content:

                break;

            default:
                break;
        }
    }

    /**
     * 初始化附件列表
     */
    private void initDataList() {
        listAddNoticeData = (RecyclerView) view.findViewById(R.id.list_add_notice_data);
        listAddNoticeData.setLayoutManager(new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mDataList = new ArrayList();
        mAddNoticeDataAdapter = new DataAdapter(mContext, mDataList);
        listAddNoticeData.setAdapter(mAddNoticeDataAdapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //
        if (requestCode == IntentUtils.OPEN_DOCUMENT_REQUEST && resultCode == getActivity().RESULT_OK) {
            Uri uri = data.getData();
            File file = FileUtils.getFileByUri((Activity) mContext, uri);
            mPresenter.uploadAttachment(mContext, file);
            DataFile dataFile = new DataFile(file.getName(), FileUtils.getFileSize(file.length()));
            mAddNoticeDataAdapter.addItem(mDataList.size(), dataFile);
        }
    }


    /**
     * 发布公告
     */
    public void publishNotice() {
        if (!etNoticeTitle.getText().toString().equals("")) {
            Notice notice = new Notice();
            notice.setC_id(37);
            notice.setContent(etAddNoticeContent.getText().toString());
            notice.setTitle(etNoticeTitle.getText().toString());
            notice.setTime(System.currentTimeMillis());
            notice.setPaths(paths.toString());
            mPresenter.publishNotice(mContext, notice);
        } else {
            new AlertDialog.Builder(mContext).setTitle("发布公告失败").setMessage("公告标题不能为空")
                    .setPositiveButton("确认",null).create().show();
        }
    }

    @Override
    public void uploadAttachmentOnComplete(String fileUrl) {
        paths.append(fileUrl).append(";");
        pb_data.setVisibility(View.GONE);
        tv_data_progress.setVisibility(View.GONE);
    }

    @Override
    public void onProgress(int value) {
//        BaseAdapter.ViewHolder viewHolder = (BaseAdapter.ViewHolder) listAddNoticeData.getChildViewHolder(listAddNoticeData.getChildAt(mDataList.size()));
//        tv_data_progress = ((TextView) viewHolder.getViewById(R.id.tv_data_progess));
//        tv_data_progress.setText(value + "%");
//        pb_data = ((ProgressBar) viewHolder.getViewById(R.id.pb_data));
//        pb_data.setProgress(value);
    }

    @Override
    public void publishNoticeOnComplete(Notice notice) {
        Intent intent = new Intent();
        intent.putExtra("notice", notice);
        ((AddNoticekActivity) mContext).setResult(RESULT, intent);
        new AlertDialog.Builder(mContext).setTitle("发布公告成功").setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((AddNoticekActivity) mContext).finish();
            }
        }).show();
    }

    @Override
    protected AddNoticePresenter createPresenter() {
        return new AddNoticePresenter();
    }
}
