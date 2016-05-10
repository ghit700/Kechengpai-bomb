package com.ketangpai.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ketangpai.bean.MessageInfo;
import com.ketangpai.bean.NotificationInfo;
import com.ketangpai.bean.PushMessage;
import com.ketangpai.event.ReceiveMessageEvent;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.push.PushConstants;

/**
 * Created by nan on 2016/4/25.
 */
public class NotificationPushMessageReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            Log.d("========", "客户端收到推送内容：" + intent.getStringExtra("msg"));
            String result = intent.getStringExtra("msg");
            JSONObject object = JSON.parseObject(result);
            PushMessage pushMessage = JSON.parseObject(object.getString("alert"), PushMessage.class);
            switch (pushMessage.getType()) {

                case 1:
                    MessageInfo messageInfo = JSON.parseObject(pushMessage.getObject(), MessageInfo.class);
                    ReceiveMessageEvent receiveMessageEvent = new ReceiveMessageEvent(messageInfo);
                    EventBus.getDefault().post(receiveMessageEvent);
                    break;

                default:
                    break;
            }
        }
    }
}
