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
public class AddHomeWorkFragment extends BaseFragment implements View.OnClickListener {

    //view
    EditText etHomeworkTitle;
    EditText etAddHomeworkContent;
    TextView tvAddHomeworkEndDate;
    TextView tvAddHomeworkEndTime;
    RecyclerView listAddHomeworkData;

    //adapter
    DataAdapter mDataAdapter;

    //变量
    List mDataList;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    private Calendar mCalendar;

    @Override

    protected int getLayoutId() {
        return R.layout.fragment_addhomework;
    }

    protected void initView() {
        etHomeworkTitle = (EditText) view.findViewById(R.id.et_homework_title);
        etAddHomeworkContent = (EditText) view.findViewById(R.id.et_add_homework_content);
        tvAddHomeworkEndDate = (TextView) view.findViewById(R.id.tv_add_homework_endDate);
        tvAddHomeworkEndTime = (TextView) view.findViewById(R.id.tv_add_homework_endTime);
        initDataList();
    }

    @Override
    protected void initData() {
        initCurrentTime();
        tvAddHomeworkEndDate.setText(TimeUtils.getCurrentDateFormat(mCalendar));
        tvAddHomeworkEndTime.setText(TimeUtils.getCurrentTimeFormat(mCalendar));

    }

    private void initCurrentTime() {
        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
    }

    @Override
    protected void initListener() {
        tvAddHomeworkEndDate.setOnClickListener(this);
        tvAddHomeworkEndTime.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_homework_endDate:
                showDateDialog();
                break;
            case R.id.tv_add_homework_endTime:
                showTimeDialog();
                break;
            case R.id.et_homework_title:

                break;
            case R.id.et_add_homework_content:

                break;

            default:
                break;
        }
    }

    /**
     * 初始化附件列表
     */
    private void initDataList() {
        listAddHomeworkData = (RecyclerView) view.findViewById(R.id.list_add_homework_data);
        listAddHomeworkData.setLayoutManager(new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mDataList = new ArrayList();
        mDataAdapter = new DataAdapter(mContext, mDataList);
        listAddHomeworkData.setAdapter(mDataAdapter);

    }

    /**
     * 时间选择器
     */
    private void showTimeDialog() {
        new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;
                mCalendar.set(Calendar.HOUR_OF_DAY, mHour);
                mCalendar.set(Calendar.MINUTE, mMinute);
                tvAddHomeworkEndTime.setText(hourOfDay + ":" + minute);
            }
        }, mHour, mMinute, true).show();
    }

    /**
     * 日期选择器
     */
    private void showDateDialog() {
        new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.i("===wu", year + " " + monthOfYear + " " + dayOfMonth);
                mYear = year;
                mMonth = monthOfYear + 1;
                mDay = dayOfMonth;
                mCalendar.set(mYear, mMonth, mDay);
                tvAddHomeworkEndDate.setText(mYear + "/" + mMonth + "/" + mDay);
            }
        }, mYear, mMonth - 1, mDay).show();
    }

    public Calendar getmCalendar() {
        return mCalendar;
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
            file.setPath(uri.getPath());
            for (int i = 0; i < 10; ++i) {

                mDataAdapter.addItem(mDataList.size(), file);
            }

        }
    }

    /**
     * 发布作业
     */
    public void sendHomeWork() {

    }
}
