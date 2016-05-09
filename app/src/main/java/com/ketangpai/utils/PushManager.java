package com.ketangpai.utils;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.ketangpai.bean.Installation;
import com.ketangpai.bean.MessageInfo;
import com.ketangpai.bean.Notification;
import com.ketangpai.bean.NotificationInfo;
import com.ketangpai.bean.PushMessage;
import com.ketangpai.bean.Student_Course;
import com.ketangpai.model.NotificationModel;
import com.ketangpai.modelImpl.NotificationModelImpl;

import java.util.List;

import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by nan on 2016/4/25.
 */
public class PushManager {


    public static void sendMessage(Context context, MessageInfo messageInfo) {
        PushMessage pushMessage = new PushMessage();
        pushMessage.setType(1);
        pushMessage.setObject(JSON.toJSONString(messageInfo));
        BmobQuery<Installation> query = new BmobQuery<Installation>();
        query.addWhereEqualTo("account", messageInfo.getReceive_account());
        BmobPushManager bmobPushManager = new BmobPushManager(context);
        bmobPushManager.setQuery(query);
        bmobPushManager.pushMessage(JSON.toJSONString(pushMessage));
    }

    ;


//    public static void publishTeacherNotification(Context context,int c_id,)


}
