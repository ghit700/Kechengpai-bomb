package com.ketangpai.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ketangpai.base.BaseFragment;
import com.ketangpai.constant.Constant;
import com.ketangpai.model.FileModel;
import com.ketangpai.modelImpl.FileModelImpl;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.ImageLoaderUtils;
import com.ketangpai.utils.IntentUtils;

import java.io.File;

import cn.bmob.v3.listener.DownloadFileListener;

/**
 * Created by Administrator on 2016/4/15.
 */
public class DataFragment extends BaseFragment implements View.OnClickListener {
    //view
    private ImageView img_data_fileImg;
    private TextView tv_data_name;
    private Button btn_data_preview;

    //变量
    private String mName;
    private String mUrl;
    private ProgressDialog mDialog;
    private FileModel fileModel;

    @Override
    protected void initVarious() {
        super.initVarious();
        if (null != getActivity().getIntent()) {
            mName = getActivity().getIntent().getStringExtra("name");
            mUrl = getActivity().getIntent().getStringExtra("url");
        }
        fileModel = new FileModelImpl();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_data;
    }

    @Override
    protected void initView() {
        img_data_fileImg = (ImageView) view.findViewById(R.id.img_data_fileImg);
        tv_data_name = (TextView) view.findViewById(R.id.tv_data_name);
        btn_data_preview = (Button) view.findViewById(R.id.btn_data_download);


    }

    @Override
    protected void initData() {
        ImageLoaderUtils.displayByFileName(mContext, img_data_fileImg, mUrl, mName);
        tv_data_name.setText(mName);
        File file = new File(Constant.ALBUM_PATH + Constant.DATA_FOLDER, mName);
        if (file.exists()) {
            btn_data_preview.setText("打开");
        }
    }

    @Override
    protected void initListener() {
        btn_data_preview.setOnClickListener(this);

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_data_download:
                showDownloadProgress();
                break;

            default:
                break;
        }
    }

    public void downloadData(String url, String fileName) {

        fileModel.downloadData(mContext, url, fileName, new DownloadFileListener() {
            @Override
            public void onSuccess(String s) {

                File file = new File(s);
                if (null != mDialog && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                sendToast("下载完成");
                Intent intent = IntentUtils.openFile(file);
                startActivity(intent);
            }

            @Override
            public void onProgress(Integer progress, long total) {
                super.onProgress(progress, total);
                mDialog.setMax((int) total);
                mDialog.setProgress(progress);
            }

            @Override
            public void onFailure(int i, String s) {
            }
        });

    }


    private void showDownloadProgress() {
        File file = new File(Constant.ALBUM_PATH + Constant.DATA_FOLDER, mName);
        if (!file.exists()) {
            downloadData(mUrl, mName);
            mDialog = new ProgressDialog(mContext);
            mDialog.setTitle("文件下载");
            mDialog.setMessage("文件下载完成百分比");
            mDialog.setCancelable(false);
            mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mDialog.setIndeterminate(false);
            mDialog.show();
        } else {
            Intent intent = IntentUtils.openFile(file);
            startActivity(intent);
        }
    }


}
