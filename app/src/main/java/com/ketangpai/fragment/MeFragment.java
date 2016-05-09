package com.ketangpai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityUnitTestCase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ketangpai.activity.AccountActivity;
import com.ketangpai.activity.LoginActivity;
import com.ketangpai.base.BaseFragment;
import com.ketangpai.constant.Constant;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.ActivityCollector;
import com.ketangpai.utils.ImageLoaderUtils;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by nan on 2016/5/9.
 */
public class MeFragment extends BaseFragment {
    @InjectView(R.id.img_account_logo)
    ImageView imgAccountLogo;
    @InjectView(R.id.tv_account)
    TextView tvAccount;
    @InjectView(R.id.tv_account_school)
    TextView tvAccountSchool;
    @InjectView(R.id.tv_account_setting)
    TextView tvAccountSetting;
    @InjectView(R.id.btn_account_exit)
    Button btnAccountExit;
    private String school;
    private String name;
    private String path;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initVarious() {
        super.initVarious();
        school = mContext.getSharedPreferences("user", 0).getString("school", "");
        name = mContext.getSharedPreferences("user", 0).getString("name", "");
        path = mContext.getSharedPreferences("user", 0).getString("path", "");
    }

    @Override
    protected void initView() {
        tvAccountSchool.setText(school);
        tvAccount.setText(name);

        ImageLoaderUtils.displayNoDisk(mContext, imgAccountLogo, Constant.LOGO_FOLDER);

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


    @OnClick({R.id.tv_account_setting, R.id.btn_account_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_account_setting:
                startActivity(new Intent(mContext, AccountActivity.class));
                break;
            case R.id.btn_account_exit:
                File file = new File(Constant.LOGO_FOLDER);
                if (file.exists()) {
                    file.delete();
                }
                mContext.getSharedPreferences("user", 0).edit().clear().commit();
                ActivityCollector.finishAllActivity();
                startActivity(new Intent(mContext, LoginActivity.class));
                getActivity().finish();
                break;
        }
    }
}
