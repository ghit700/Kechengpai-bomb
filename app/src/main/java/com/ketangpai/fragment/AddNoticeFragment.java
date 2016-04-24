package com.ketangpai.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ketangpai.activity.AddNoticekActivity;
import com.ketangpai.adapter.DataAdapter;
import com.ketangpai.base.BasePresenterFragment;
import com.ketangpai.bean.Notice;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.presenter.AddNoticePresenter;
import com.ketangpai.utils.FileUtils;
import com.ketangpai.utils.IntentUtils;
import com.ketangpai.view.FullyLinearLayoutManager;
import com.ketangpai.viewInterface.AddNoticeViewInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

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
    private List mDataList;
    private List<BmobFile> mFiles;
    private TextView tv_data_progress;
    private ProgressBar pb_data;
    private int c_id;
    public static final int RESULT = 12;
    private int mValue = 0;
    private Notice notice;

    @Override
    protected void initVarious() {
        super.initVarious();
        mFiles = new ArrayList();
        c_id = getActivity().getIntent().getIntExtra("c_id", -1);
        if (null != getActivity().getIntent().getSerializableExtra("notice")) {
            notice = (Notice) getActivity().getIntent().getSerializableExtra("notice");
        }
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
            BmobFile bmobFile = new BmobFile(file);

            mPresenter.uploadAttachment(mContext, bmobFile);
            mAddNoticeDataAdapter.addItem(mDataList.size(), file.getName());
        }
    }


    /**
     * 发布公告
     */
    public void publishNotice() {
        if (0 != mValue && 100 != mValue) {
            new AlertDialog.Builder(mContext).setTitle("文件还没上传完毕,请稍等...").setPositiveButton("确认", null).show();
        } else if (!etNoticeTitle.getText().toString().equals("")) {
            Notice notice = new Notice();
            notice.setC_id(c_id);
            notice.setContent(etAddNoticeContent.getText().toString());
            notice.setTitle(etNoticeTitle.getText().toString());
            notice.setTime(System.currentTimeMillis());
            notice.setFiles(mFiles);
            mPresenter.publishNotice(mContext, notice);
        } else {
            new AlertDialog.Builder(mContext).setTitle("发布公告失败").setMessage("公告标题不能为空")
                    .setPositiveButton("确认", null).create().show();
        }
    }

    @Override
    public void uploadAttachmentOnComplete(BmobFile bmobFile) {
        mFiles.add(bmobFile);
//        pb_data.setVisibility(View.GONE);
//        tv_data_progress.setVisibility(View.GONE);
    }

    @Override
    public void onProgress(int value) {
        this.mValue = value;
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
