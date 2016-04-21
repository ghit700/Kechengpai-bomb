package com.ketangpai.model;

import android.content.Context;

import com.ketangpai.Callback.ResultsCallback;
import com.ketangpai.bean.User;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by nan on 2016/4/16.
 */
public interface UserModel {
    public void login(Context context, String account, String password, ResultsCallback ResultsCallback);

    public void register(Context context, User user, SaveListener resultCallback);

    public void updateUserInfo(Context context, String u_id, String columnName, String columnValue, UpdateListener resultCallback);
}
