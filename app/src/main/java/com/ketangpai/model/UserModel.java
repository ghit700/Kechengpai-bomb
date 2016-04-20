package com.ketangpai.model;

import com.ketangpai.bean.User;
import com.ketangpai.utils.VolleyUtils;

/**
 * Created by nan on 2016/4/16.
 */
public interface UserModel {
    public void login(String account, String password, VolleyUtils.ResultCallback resultCallback);

    public void register(User user, VolleyUtils.ResultCallback resultCallback);

    public void updateUserInfo(String account, String columnName, String columnValue, VolleyUtils.ResultCallback resultCallback);
}
