package com.ketangpai.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ketangpai.base.BaseFragment;
import com.ketangpai.nan.ketangpai.R;

/**
 * Created by Administrator on 2016/4/15.
 */
public class DataFragment extends BaseFragment implements View.OnClickListener {

    //view
    private ImageView img_data_fileImg;
    private TextView tv_data_name;
    private TextView tv_data_size;
    private Button btn_data_delete;
    private Button btn_data_preview;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_data;
    }

    @Override
    protected void initView() {
        img_data_fileImg = (ImageView) view.findViewById(R.id.img_data_fileImg);
        tv_data_name = (TextView) view.findViewById(R.id.tv_data_name);
        tv_data_size = (TextView) view.findViewById(R.id.tv_data_size);
        btn_data_delete = (Button) view.findViewById(R.id.btn_data_delete);
        btn_data_preview = (Button) view.findViewById(R.id.btn_data_preview);
    }

    @Override
    protected void initData() {

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
            case R.id.btn_data_preview:
                break;

            default:
                break;
        }
    }
}
