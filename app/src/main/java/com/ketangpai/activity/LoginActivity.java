package com.ketangpai.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ketangpai.base.BaseActivity;
import com.ketangpai.bean.User;
import com.ketangpai.callback.ResultCallback;
import com.ketangpai.constant.Constant;
import com.ketangpai.model.UserModel;
import com.ketangpai.modelImpl.UserModelImpl;
import com.ketangpai.nan.ketangpai.R;
import com.ketangpai.utils.ActivityCollector;
import com.ketangpai.utils.ImageLoaderUtils;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by nan on 2016/3/9.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    public static final String TAG = "===LoginActivity";

    //    view
    private EditText mName, mPassword;
    private TextView mRegister;
    private Button mBtn_login;
    private ImageView mUserIconImg;

    //变量
    //软键盘
    private InputMethodManager mimm;
    //保存点击的时间
    private long exitTime;
    private AlertDialog RegisterDialog;
    private User mUser;
    private String account;
    private UserModel userModel;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initVariables() {
        super.initVariables();
        account = getSharedPreferences("user", 0).getString("account", "");
        userModel = new UserModelImpl();
    }

    @Override
    protected void initView() {
        initEtNameAndPassword();
        mBtn_login = (Button) findViewById(R.id.btn_login_login);
        mRegister = (TextView) findViewById(R.id.tv_login_register);
        mUserIconImg = (ImageView) findViewById(R.id.img_login_logo);


    }

    @Override
    protected void initData() {
        mimm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    protected void initListener() {
        mBtn_login.setOnClickListener(this);
        mName.setOnClickListener(this);
        mPassword.setOnClickListener(this);
        mRegister.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }


    //初始化nameEdittext和passwordEdittext

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_login:
                mimm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                login(mName.getText().toString(), mPassword.getText().toString());
                break;
            case R.id.et_login_name:
                mimm.showSoftInput(null, InputMethodManager.SHOW_FORCED);
                break;
            case R.id.et_login_password:
                mimm.showSoftInput(null, InputMethodManager.SHOW_FORCED);
                break;
            case R.id.tv_login_register:
                showRegisterDialog();
                mimm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            default:
                break;
        }
    }


    private void initEtNameAndPassword() {

        //设置用户名左边drawable
        mName = (EditText) findViewById(R.id.et_login_name);
        Drawable nameDrawableLeft = getResources().getDrawable(R.drawable.icon_user_username);
        nameDrawableLeft.setBounds(0, 0, 45, 45);
        mName.setCompoundDrawables(nameDrawableLeft, null, null, null);
        mName.setCompoundDrawablePadding(20);

        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!account.equals("") && account.equals(mName.getText().toString())) {
                    ImageLoaderUtils.displayNoDisk(mContext, mUserIconImg, Constant.LOGO_FOLDER);
                } else {
                    ImageLoaderUtils.display(mContext, mUserIconImg, "");
                }
            }
        });

        mPassword = (EditText) findViewById(R.id.et_login_password);
        Drawable passwordDrawableLeft = getResources().getDrawable(R.drawable.icon_user_password);
        passwordDrawableLeft.setBounds(0, 0, 45, 45);
        mPassword.setCompoundDrawables(passwordDrawableLeft, null, null, null);
        mPassword.setCompoundDrawablePadding(20);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //双击退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                showToast("再按一次退出", 0);
                exitTime = System.currentTimeMillis();
                return true;
            } else {

                ActivityCollector.finishAllActivity();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showRegisterDialog() {
        RegisterDialog = new AlertDialog.Builder(mContext).create();
        RegisterDialog.setCanceledOnTouchOutside(false);
        View view;


        view = LayoutInflater.from(mContext).inflate(R.layout.dialog_register_student, null);
        final EditText et_register_account = (EditText) view.findViewById(R.id.et_register_account);
        final EditText et_register_name = (EditText) view.findViewById(R.id.et_register_name);
        final EditText et_register_sid = (EditText) view.findViewById(R.id.et_register_sid);
        final EditText et_register_password = (EditText) view.findViewById(R.id.et_register_password);
        final EditText et_register_school = (EditText) view.findViewById(R.id.et_register_school);
        Button btn_register = (Button) view.findViewById(R.id.btn_register);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterDialog.dismiss();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_register_account.getText().toString().equals("") &&
                        !et_register_password.getText().toString().equals("") &&
                        !et_register_school.getText().toString().equals("") &&
                        !et_register_name.getText().toString().equals("")) {

                    mUser = new User();

                    mUser.setAccount(et_register_account.getText().toString());
                    mUser.setPassword(et_register_password.getText().toString());
                    mUser.setName(et_register_name.getText().toString());
                    mUser.setNumber(Integer.parseInt(et_register_sid.getText().toString()));
                    mUser.setSchool(et_register_school.getText().toString());
                    mUser.setType(1);
                    register(mUser);

                } else {
                    showRegisterFailDialog();
                }

            }
        });

        RegisterDialog.setView(view);
        RegisterDialog.show();

    }

    private void showRegisterFailDialog() {
        new AlertDialog.Builder(mContext).setTitle("注册失败").setMessage("不能为空,请填写")
                .setPositiveButton("确认", null).setNegativeButton("取消", null).create().show();
    }


    public void login(String account, String password) {

        showLoadingDialog();
        setLoadingText("登录中...");

        userModel.login(mContext, account, password, new ResultCallback() {
            @Override
            public void onSuccess(Object object) {
                dismissLoadingDialog();
                User user = (User) object;
                if (null != user) {
                    mUser = user;
                    saveUserMessage();

                } else {
                    new AlertDialog.Builder(mContext).setTitle("登录失败")
                            .setMessage("用户名或密码错误,请重新输入")
                            .setPositiveButton("确认", null).show();
                    dismissLoadingDialog();

                }
            }

            @Override
            public void onFailure(String e) {

            }
        });
    }

    public void register(User user) {
        showLoadingDialog();
        setLoadingText("注册中...");


        userModel.register(mContext, user, new SaveListener() {
            @Override
            public void onSuccess() {
                RegisterDialog.dismiss();
                new AlertDialog.Builder(mContext).setTitle("注册成功")
                        .setMessage("恭喜您,注册成功课程派")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveUserMessage();
                            }
                        }).show();


            }

            @Override
            public void onFailure(int i, String s) {
                dismissLoadingDialog();
                new AlertDialog.Builder(mContext).setTitle("注册失败")
                        .setMessage("已存在相同的用户名,请重新输入")
                        .setPositiveButton("确认", null).show();

            }
        });


    }


    /**
     * 保存用户信息
     */
    private void saveUserMessage() {
        SharedPreferences sp = getSharedPreferences("user", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("account", mUser.getAccount());
        editor.putString("password", mUser.getPassword());
        editor.putString("school", mUser.getSchool());
        editor.putInt("number", mUser.getNumber());
        editor.putString("name", mUser.getName());
        editor.putString("u_id", mUser.getObjectId());
        editor.putString("path", mUser.getPath());
        editor.commit();
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }
}
