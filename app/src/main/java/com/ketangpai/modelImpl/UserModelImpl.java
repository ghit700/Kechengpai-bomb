package com.ketangpai.modelImpl;

import android.content.Context;
import android.util.Log;

import com.ketangpai.Callback.ResultCallback;
import com.ketangpai.Callback.ResultsCallback;
import com.ketangpai.bean.User;
import com.ketangpai.constant.Constant;
import com.ketangpai.fragment.AccountFragment;
import com.ketangpai.fragment.AccountUpdateFragment;
import com.ketangpai.model.UserModel;
import com.ketangpai.utils.FileUtils;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by nan on 2016/4/16.
 */
public class UserModelImpl implements UserModel {


    @Override
    public void login(final Context context, String account, String password, final ResultCallback resultCallback) {


        Log.i(AccountUpdateFragment.TAG, "login account=" + account + " password=" + password);
        String sql = "select * from User where password=? and account=?";
        BmobQuery<User> query = new BmobQuery<User>("User");
        query.doSQLQuery(context, sql, new SQLQueryListener<User>() {
            @Override
            public void done(BmobQueryResult<User> bmobQueryResult, BmobException e) {
                List list = bmobQueryResult.getResults();
                if (null != list) {
                    final User user = (User) list.get(0);
                    if (!"".equals(user.getPath())) {
                        FileUtils.createNewFile(Constant.PHOTO_FOLDER);
                        final BmobFile bmobFile = new BmobFile("logo.jpg", "", user.getPath());
                        bmobFile.download(context, new File(Constant.LOGO_FOLDER), new DownloadFileListener() {
                            @Override
                            public void onSuccess(String s) {
                                Log.i("=====", "filename=" + bmobFile.getFilename());

                                resultCallback.onSuccess(user);
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                resultCallback.onFailure(s);
                            }
                        });
                    } else {
                        resultCallback.onSuccess(user);
                    }
                } else {
                    resultCallback.onFailure(e.getMessage());
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

    @Override
    public void uploadUserLogo(final Context context, File file, final User user, final UpdateListener resultCallback) {
        Log.i(AccountFragment.TAG, "uploadUserLogo filepath=" + file.getAbsolutePath() + " name=" + file.getName());
        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.upload(context, new UploadFileListener() {
            @Override
            public void onSuccess() {
                user.setPath(bmobFile.getFileUrl(context));
                user.update(context, user.getObjectId(), resultCallback);
            }

            @Override
            public void onFailure(int i, String s) {
                Log.i(AccountFragment.TAG, s);
            }
        });
    }


}
