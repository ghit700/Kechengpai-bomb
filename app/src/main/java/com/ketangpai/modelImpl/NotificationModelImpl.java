package com.ketangpai.modelImpl;

import android.content.Context;
import android.util.Log;

import com.ketangpai.bean.Test;
import com.ketangpai.callback.ResultsCallback;
import com.ketangpai.bean.Data;
import com.ketangpai.bean.Notice;
import com.ketangpai.bean.Notification;
import com.ketangpai.bean.NotificationInfo;
import com.ketangpai.bean.Teacher_Homework;
import com.ketangpai.fragment.NotificationFragment;
import com.ketangpai.model.NotificationModel;
import com.ketangpai.utils.PushManager;
import com.ketangpai.utils.TimeUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by nan on 2016/4/25.
 */
public class NotificationModelImpl implements NotificationModel {


    @Override
    public void getNotificationList(Context context, String account, final ResultsCallback resultsCallback) {
        Log.i(NotificationFragment.TAG, "getNotificationList account=" + account);
        String sql = "select * from Notification where account=? order by time desc";
        BmobQuery<Notification> query = new BmobQuery<>();
        query.doSQLQuery(context, sql, new SQLQueryListener<Notification>() {
            @Override
            public void done(BmobQueryResult<Notification> bmobQueryResult, BmobException e) {
                List<Notification> notifications = bmobQueryResult.getResults();
                if (null != notifications) {
                    resultsCallback.onSuccess(notifications);
                }
            }
        }, account);
    }
}

