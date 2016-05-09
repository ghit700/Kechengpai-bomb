package com.ketangpai.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ketangpai.nan.ketangpai.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by nan on 2016/3/14.
 */
public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected View view;
    private Dialog mLoadingDialog;
    private TextView tv_loading;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVarious();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.inject(this, view);
        initView();
        initData();
        initListener();
        loadData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    protected void initVarious() {

    }

    ;

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void loadData();

    protected void sendToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
    }
    public void showLoadingDialog() {
        mLoadingDialog = new AlertDialog.Builder(mContext).show();
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null);
        tv_loading = (TextView) view.findViewById(R.id.tv_loading);
        mLoadingDialog.setContentView(view);
        mLoadingDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 修改载入过程的文字
     *
     * @param text
     */
    public void setLoadingText(String text) {
        tv_loading.setText(text);
    }


    public void dismissLoadingDialog() {
        mLoadingDialog.dismiss();
    }



}
