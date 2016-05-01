package com.ketangpai.viewInterface;

import com.ketangpai.bean.Notification;
import com.ketangpai.bean.NotificationInfo;

import java.util.List;

/**
 * Created by nan on 2016/4/26.
 */
public interface NotificationViewInterface {
    void getNotificationListOnComplete(List<Notification> notifications);
}
