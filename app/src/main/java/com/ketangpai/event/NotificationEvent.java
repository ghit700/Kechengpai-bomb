package com.ketangpai.event;

import com.ketangpai.bean.NotificationInfo;

/**
 * Created by nan on 2016/4/25.
 */
public class NotificationEvent {
    NotificationInfo notificationInfo;
    String time;

    public NotificationInfo getNotificationInfo() {
        return notificationInfo;
    }

    public void setNotificationInfo(NotificationInfo notificationInfo) {
        this.notificationInfo = notificationInfo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
