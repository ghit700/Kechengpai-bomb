package com.ketangpai.model;

import android.content.Context;

import com.ketangpai.callback.ResultsCallback;
import com.ketangpai.bean.NotificationInfo;

/**
 * Created by nan on 2016/4/25.
 */
public interface NotificationModel {


    void getNotificationList(Context context, String account, ResultsCallback resultsCallback);
}
