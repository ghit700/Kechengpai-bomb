package com.ketangpai.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ketangpai.adapter.DataAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.bean.DocumentFile;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.FileUtils;
import com.ketangpai.utils.IntentUtils;
import com.ketangpai.utils.TimeUtils;
import com.ketangpai.view.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nan on 2016/3/27.
 */
public class AddNoticeFragment extends BaseFragment implements View.OnClickListener {

    //view
    EditText etNoticeTitle;
    EditText etAddNoticeContent;

    RecyclerView listAddNoticeData;

    //adapter
    DataAdapter mAddNoticeDataAdapter;

    //变量
    List mDataList;

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
            String fileName = FileUtils.getFileName(uri);
            int fileType = FileUtils.getFileType(fileName);
            String size = FileUtils.getFileSize(uri);
            DocumentFile file = new DocumentFile(fileType, fileName, size);
            for (int i = 0; i < 10; ++i) {

                mAddNoticeDataAdapter.addItem(mDataList.size(), file);
            }

        }
    }

    /**
     * 发布作业
     */
    public void sendNotice() {

    }
}
