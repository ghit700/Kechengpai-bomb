package com.ketangpai.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ketangpai.base.BaseFragment;
import com.ketangpai.base.BasePresenterFragment;
import com.ketangpai.constant.Constant;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.presenter.DataPresenter;
import com.ketangpai.utils.ImageLoaderUtils;
import com.ketangpai.utils.IntentUtils;
import com.ketangpai.viewInterface.DataViewInterface;

import java.io.File;

/**
 * Created by Administrator on 2016/4/15.
 */
public class DataFragment extends BasePresenterFragment<DataViewInterface, DataPresenter> implements DataViewInterface, View.OnClickListener {
    public static final String TAG = "===DataFragment";
    //view
    private ImageView img_data_fileImg;
    private TextView tv_data_name;
    private Button btn_data_delete;
    private Button btn_data_preview;

    //变量
    private String mName;
    private String mUrl;
    private ProgressDialog mDialog;

    @Override
    protected void initVarious() {
        super.initVarious();
        if (null != getActivity().getIntent()) {
            mName = getActivity().getIntent().getStringExtra("name");
            mUrl = getActivity().getIntent().getStringExtra("url");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_data;
    }

    @Override
    protected void initView() {
        img_data_fileImg = (ImageView) view.findViewById(R.id.img_data_fileImg);
        tv_data_name = (TextView) view.findViewById(R.id.tv_data_name);
        btn_data_delete = (Button) view.findViewById(R.id.btn_data_delete);
        btn_data_preview = (Button) view.findViewById(R.id.btn_data_download);
    }

    @Override
    protected void initData() {
        ImageLoaderUtils.display(mContext, img_data_fileImg, mUrl);
        tv_data_name.setText(mName);
    }

    @Override
    protected void initListener() {
        btn_data_preview.setOnClickListener(this);
        btn_data_delete.setOnClickListener(this);

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_data_delete:
                break;
            case R.id.btn_data_download:
                showDownloadProgress();
                break;

            default:
                break;
        }
    }

    private void showDownloadProgress() {
        File file = new File(Constant.ALBUM_PATH + Constant.DATA_FOLDER, mName);
        if (!file.exists()) {
            mPresenter.downloadData(mContext, mUrl, mName);
            mDialog = new ProgressDialog(mContext);
            mDialog.setTitle("文件下载");
            mDialog.setMessage("文件下载完成百分比");
            mDialog.setCancelable(false);
            mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mDialog.setIndeterminate(false);
            mDialog.show();
        } else {
            IntentUtils.openFile(this, Uri.fromFile(file));
        }
    }

    @Override
    public void onProgress(int value, long total) {
        mDialog.setMax((int) total);
        mDialog.setProgress(value);
    }

    @Override
    public void downloadOnComplete(File file) {
        if (null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        sendToast("下载完成");
        IntentUtils.openFile(this, Uri.fromFile(file));
    }

    @Override
    protected DataPresenter createPresenter() {
        return new DataPresenter();
    }
}
