package com.ketangpai.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ketangpai.activity.AccountUpdateActivity;
import com.ketangpai.base.BasePresenterFragment;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.presenter.AccountUpdatePresenter;
import com.ketangpai.utils.ImageLoaderUtils;
import com.ketangpai.utils.IntentUtils;
import com.ketangpai.viewInterface.AccountUpdateViewInterface;

/**
 * Created by nan on 2016/3/21.
 */
public class AccountFragment extends BasePresenterFragment<AccountUpdateViewInterface, AccountUpdatePresenter> implements View.OnClickListener {

    RelativeLayout mUserIcon, mName, mShool, mNumber, mPassword;
    TextView tv_account, tv_school, tv_name, tv_number;
    ImageView img_account_user;
    //变量
    private int type;
    private String school;
    private int number;
    private String name;
    private String account;
    private int REQUEST = 100;

    @Override
    protected void initVarious() {
        super.initVarious();
        school = mContext.getSharedPreferences("user", 0).getString("school", "");
        name = mContext.getSharedPreferences("user", 0).getString("name", "");
        account = mContext.getSharedPreferences("user", 0).getString("account", "");
        type = mContext.getSharedPreferences("user", 0).getInt("type", -1);
        number = mContext.getSharedPreferences("user", 0).getInt("number", -1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account;
    }

    @Override
    protected void initView() {
        mUserIcon = (RelativeLayout) view.findViewById(R.id.ll_account_userIcon);
        mShool = (RelativeLayout) view.findViewById(R.id.ll_account_school);
        mName = (RelativeLayout) view.findViewById(R.id.ll_account_name);
        mNumber = (RelativeLayout) view.findViewById(R.id.ll_account_sid);
        mPassword = (RelativeLayout) view.findViewById(R.id.ll_account_password);
        tv_account = (TextView) view.findViewById(R.id.tv_account);
        tv_school = (TextView) view.findViewById(R.id.tv_school);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_number = (TextView) view.findViewById(R.id.tv_number);
        img_account_user = (ImageView) view.findViewById(R.id.img_account_user);

        if (type == 0) {
            mNumber.setVisibility(view.GONE);
        }
    }

    @Override
    protected void initData() {
        tv_account.setText(account);
        tv_school.setText(school);
        tv_name.setText(name);

        if (type == 1) {
            tv_number.setText(String.valueOf(number));
        }
    }

    @Override
    protected void initListener() {
        mName.setOnClickListener(this);
        mUserIcon.setOnClickListener(this);
        mShool.setOnClickListener(this);
        mNumber.setOnClickListener(this);
        mPassword.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected AccountUpdatePresenter createPresenter() {
        return new AccountUpdatePresenter();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_account_name:
                intent = new Intent(mContext, AccountUpdateActivity.class);
                intent.putExtra("columnName", "姓名");
                startActivityForResult(intent, REQUEST);
                break;
            case R.id.ll_account_school:
                intent = new Intent(mContext, AccountUpdateActivity.class);
                intent.putExtra("columnName", "学校");
                startActivityForResult(intent, REQUEST);
                break;
            case R.id.ll_account_userIcon:
//                showUpdateHeadDialog("修改头像","拍照","从相册中选择")
                IntentUtils.openImageFile(this);
                break;
            case R.id.ll_account_sid:
                intent = new Intent(mContext, AccountUpdateActivity.class);
                intent.putExtra("columnName", "学号");
                startActivityForResult(intent, REQUEST);
                break;
            case R.id.ll_account_password:
                intent = new Intent(mContext, AccountUpdateActivity.class);
                intent.putExtra("columnName", "密码");
                startActivityForResult(intent, REQUEST);
                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == IntentUtils.OPEN_IMGAE && resultCode == getActivity().RESULT_OK) {
            Log.i("===w", data.getData().getPath());
            ImageLoaderUtils.display(mContext, img_account_user, data.getData().getPath());
        }

        if (requestCode == REQUEST && resultCode == AccountUpdateActivity.RESULT_OK) {
            if (null != data.getStringExtra("columnCode") && null != data.getStringExtra("columnValue")) {
                String columnCode = data.getStringExtra("columnCode");
                String columnValue = data.getStringExtra("columnValue");
                Log.i(AccountUpdateFragment.TAG, "onActivityResult columnCode=" + columnCode + " columnValue=" + columnValue);
                switch (columnCode) {
                    case "password":
                        break;

                    case "school":
                        tv_school.setText(columnValue);
                        break;
                    case "number":
                        tv_number.setText(columnValue);
                        break;
                    case "name":
                        tv_name.setText(columnValue);
                        break;
                    default:
                        break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}


