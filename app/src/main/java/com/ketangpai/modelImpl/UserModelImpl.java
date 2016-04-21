package com.ketangpai.modelImpl;

import android.content.Context;
import android.util.Log;

import com.ketangpai.Callback.ResultsCallback;
import com.ketangpai.bean.User;
import com.ketangpai.fragment.AccountUpdateFragment;
import com.ketangpai.model.UserModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by nan on 2016/4/16.
 */
public class UserModelImpl implements UserModel {


    @Override
    public void login(Context context, String account, String password, final ResultsCallback resultsCallback) {


        Log.i(AccountUpdateFragment.TAG, "login account=" + account + " password=" + password);
        String sql = "select * from User where password=? and account=?";
        BmobQuery<User> query = new BmobQuery<User>("User");
        query.doSQLQuery(context, sql, new SQLQueryListener<User>() {
            @Override
            public void done(BmobQueryResult<User> bmobQueryResult, BmobException e) {
                List list = bmobQueryResult.getResults();
                if (null != list) {
                    resultsCallback.onSuccess(list);
                } else {
                    resultsCallback.onFailure(e);
                }
            }
        }, password, account);
    }

    @Override
    public void register(Context context, User user, SaveListener resultCallBack) {

        Log.i(AccountUpdateFragment.TAG, "register _id" + user.getObjectId() + " name=" + user.getName() + " account=" + user.getAccount() + "  type=" + user.getType()
                + " password=" + user.getPassword() + " school=" + user.getSchool() + " number=" + user.getNumber());

        user.save(context, resultCallBack);

    }


    @Override
    public void updateUserInfo(Context context, String u_id, String columnName, String columnValue, UpdateListener resultCallback) {
        User user = new User();
        Log.i(AccountUpdateFragment.TAG, "updateUserInfo colunmnName=" + columnName + "  value=" + columnValue);
        switch (columnName) {
            case "password":
                user.setPassword(columnValue);
                break;
            case "school":
                user.setSchool(columnValue);
                break;
            case "name":
                user.setName(columnValue);
                break;
            case "number":
                user.setNumber(Integer.parseInt(columnValue));
                break;

            default:
                break;
        }

        user.update(context, u_id, resultCallback);


    }

}
