package com.ketangpai.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ketangpai.nan.ketangpai.R;

/**
 * Created by nan on 2016/3/14.
 */
public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    protected View view;

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
        initView();
        initData();
        initListener();
        loadData();
        return view;
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


}
