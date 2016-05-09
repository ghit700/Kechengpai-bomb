package com.ketangpai.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ketangpai.activity.DataActivity;
import com.ketangpai.adapter.DataAdapter;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.bean.Data;
import com.ketangpai.bean.Student_Homework;
import com.ketangpai.bean.Teacher_Homework;
import com.ketangpai.callback.AttachmentResultCallback;
import com.ketangpai.callback.ResultCallback;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.model.FileModel;
import com.ketangpai.model.HomeworkModel;
import com.ketangpai.modelImpl.FileModelImpl;
import com.ketangpai.modelImpl.HomeworkModelImpl;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.FileUtils;
import com.ketangpai.utils.IntentUtils;
import com.ketangpai.utils.TimeUtils;
import com.ketangpai.view.FullyLinearLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by nan on 2016/5/1.
 */
public class HomeworkFragment extends BaseFragment {
    @InjectView(R.id.tv_s_homework_publishTime)
    TextView tvSHomeworkPublishTime;
    @InjectView(R.id.tv_s_homework_content)
    TextView tvSHomeworkContent;
    @InjectView(R.id.list_attach_data)
    RecyclerView listAttachData;
    @InjectView(R.id.list_homework)
    RecyclerView listHomework;
    @InjectView(R.id.tv_s_homework_comment)
    TextView tvSHomeworkComment;
    @InjectView(R.id.tv_s_homework_grade)
    TextView tvSHomeworkGrade;
    @InjectView(R.id.btn_upload_homework)
    Button btnUploadHomework;
    @InjectView(R.id.tv_s_homework_endTime)
    TextView tvSHomeworkEndTime;

    //adapter
    private DataAdapter mHomeworkAdapter;
    private DataAdapter mAttachAdapter;

    //various
    private List<Data> mHomeworks;
    private List<Data> mAttachs;
    private Teacher_Homework teacher_homework;
    private int mValue;
    private String account;
    private Student_Homework student_homework;
    private FileModel fileModel;
    private HomeworkModel homeworkModel;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homework;
    }

    @Override
    protected void initVarious() {
        super.initVarious();
        teacher_homework = (Teacher_Homework) getActivity().getIntent().getSerializableExtra("homework");
        account = getActivity().getSharedPreferences("user", 0).getString("account", "");
        fileModel = new FileModelImpl();
        homeworkModel = new HomeworkModelImpl();

    }

    @Override
    protected void initView() {
        listAttachData.setLayoutManager(new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAttachs = teacher_homework.getFiles();
        mAttachAdapter = new DataAdapter(mContext, mAttachs);
        listAttachData.setAdapter(mAttachAdapter);


        listHomework.setLayoutManager(new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mHomeworks = new ArrayList<>();
        mHomeworkAdapter = new DataAdapter(mContext, mHomeworks);
        listHomework.setAdapter(mHomeworkAdapter);

        tvSHomeworkComment.setText("");
        tvSHomeworkContent.setText(teacher_homework.getContent());
        tvSHomeworkPublishTime.setText(TimeUtils.getNoticeTime(teacher_homework.getP_time()));
        tvSHomeworkEndTime.setText("截至:" + TimeUtils.getNoticeTime(teacher_homework.getE_time()));
        tvSHomeworkGrade.setText("");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mHomeworkAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext, DataActivity.class);
                intent.putExtra("name", mHomeworks.get(position).getName());
                intent.putExtra("url", mHomeworks.get(position).getUrl());
                startActivity(intent);
            }
        });
        mAttachAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext, DataActivity.class);
                intent.putExtra("name", mAttachs.get(position).getName());
                intent.putExtra("url", mAttachs.get(position).getUrl());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void loadData() {
        getStudentHomework(teacher_homework, account);
    }


    @OnClick(R.id.btn_upload_homework)
    public void onBtn_upload_homeworkClick() {
        if (btnUploadHomework.getText().equals("上传作业")) {
            IntentUtils.openDocument(this);
        } else {
            student_homework.setHomeworks(mHomeworks);
            if (teacher_homework.getE_time() < System.currentTimeMillis()) {
                student_homework.setS_state("逾时未交");
            } else {
                student_homework.setS_state("按时交");
            }
            student_homework.setT_state("未批改");
            student_homework.setCommit_time(System.currentTimeMillis());
            homeworkModel.publishStudentHomework(mContext, student_homework);
            getActivity().finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentUtils.OPEN_DOCUMENT_REQUEST && resultCode == getActivity().RESULT_OK) {
            Uri uri = data.getData();
            File file = FileUtils.getFileByUri((Activity) mContext, uri);
            BmobFile bmobFile = new BmobFile(file);
            Data data1 = new Data();
            data1.setName(file.getName());
            data1.setSize(FileUtils.getFileSize(file.length()));
            data1.setUrl(file.getAbsolutePath());
            uploadHomework(bmobFile);
            mHomeworkAdapter.addItem(mHomeworks.size(), data1);
            mHomeworkAdapter.notifyDataSetChanged();
            btnUploadHomework.setText("确认上传");
        }
    }


    public void uploadHomework(final BmobFile file) {

        fileModel.uploadAttachment(mContext, file, new AttachmentResultCallback() {


            @Override
            public void onSuccess(BmobFile bmobFile) {
                Data data = new Data();
                data.setUrl(bmobFile.getFileUrl(mContext));
                data.setSize(FileUtils.getFileSize(file.getLocalFile().length()));
                data.setName(file.getFilename());
                mHomeworks.set(mHomeworks.size(), data);
            }

            @Override
            public void onProgress(Integer value) {

                mValue = value;
            }

            @Override
            public void onFailure(String e) {

            }
        });

    }


    public void getStudentHomework(Teacher_Homework homework, String account) {

        homeworkModel.getStudentHomewokr(mContext, homework, account, new ResultCallback() {
            @Override
            public void onSuccess(Object object) {
                student_homework = (Student_Homework) object;
                if (!student_homework.getS_state().equals("未交")) {
                    btnUploadHomework.setVisibility(View.GONE);
                }
                if (null != student_homework.getHomeworks()) {
                    mHomeworks.addAll(student_homework.getHomeworks());
                    Collections.reverse(mHomeworks);
                    mHomeworkAdapter.notifyDataSetChanged();
                }
                if (null == student_homework.getGrade()) {
                    tvSHomeworkGrade.setText("");
                } else {
                    tvSHomeworkGrade.setText(String.valueOf(student_homework.getGrade()));

                }
                tvSHomeworkComment.setText(student_homework.getComment());
            }

            @Override
            public void onFailure(String e) {

            }
        });

    }


}
