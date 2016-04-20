package com.ketangpai.modelImpl;

import android.util.Log;

import com.ketangpai.activity.AccountUpdateActivity;
import com.ketangpai.activity.LoginActivity;
import com.ketangpai.bean.User;
import com.ketangpai.constant.Urls;
import com.ketangpai.fragment.AccountFragment;
import com.ketangpai.fragment.AccountUpdateFragment;
import com.ketangpai.model.UserModel;
import com.ketangpai.utils.VolleyUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nan on 2016/4/16.
 */
public class UserModelImpl implements UserModel {
    Map<String, String> params;

    @Override
    public void login(String account, String password, VolleyUtils.ResultCallback resultCallback) {
        params = new HashMap<>();
        params.put("account", account);
        params.put("password", password);

        Log.i(AccountUpdateFragment.TAG, "login account=" + account + " password=" + password);
        String url = Urls.SERVER_HOST + Urls.LOGIN;
        VolleyUtils.post(url, LoginActivity.TAG, params, resultCallback);
    }

    @Override
    public void register(User user, VolleyUtils.ResultCallback resultCallback) {
        params = new HashMap<>();
        params.put("name", user.getName());
        params.put("account", user.getAccount());
        params.put("type", String.valueOf(user.getType()));
        params.put("password", user.getPassword());
        params.put("school", user.getSchool());
        params.put("number", String.valueOf(user.getNumber()));

        Log.i(AccountUpdateFragment.TAG, "register  name=" + user.getName() + " account=" + user.getAccount() + "  type=" + user.getType()
                + " password=" + user.getPassword() + " school=" + user.getSchool() + " number=" + user.getNumber());

        String url = Urls.SERVER_HOST + Urls.REGISTER;
        VolleyUtils.post(url, LoginActivity.TAG, params, resultCallback);
    }

    @Override
    public void updateUserInfo(String account, String columnName, String columnValue, VolleyUtils.ResultCallback resultCallback) {
        params = new HashMap<>();
        params.put("account", account);
        params.put("columnName", columnName);
        params.put("colunmValue", columnValue);

        Log.i(AccountUpdateFragment.TAG, "updateUserInfo account=" + account + " colunmnName=" + columnName + "  value=" + columnValue);
        String url = Urls.SERVER_HOST + Urls.UPDATE_USER_INFO;
        VolleyUtils.post(url, AccountUpdateFragment.TAG, params, resultCallback);
    }

}
