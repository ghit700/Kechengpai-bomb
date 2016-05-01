package com.ketangpai.presenter;

import android.content.Context;

import com.ketangpai.callback.ResultsCallback;
import com.ketangpai.base.BasePresenter;
import com.ketangpai.model.NotificationModel;
import com.ketangpai.modelImpl.NotificationModelImpl;
import com.ketangpai.viewInterface.NotificationViewInterface;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by nan on 2016/4/26.
 */
public class NotificationPresenter extends BasePresenter<NotificationViewInterface> {
    NotificationViewInterface mNotificationViewInterface;
    NotificationModel mNotificationModel;

    public NotificationPresenter() {
        mNotificationModel = new NotificationModelImpl();
    }

    public void getNotificationList(Context context, String account) {
        if (isViewAttached()) {
            mNotificationViewInterface = getView();
            mNotificationModel.getNotificationList(context, account, new ResultsCallback() {
                @Override
                public void onSuccess(List list) {

                    mNotificationViewInterface.getNotificationListOnComplete(list);
                }

                @Override
                public void onFailure(BmobException e) {

                }
            });
        }
    }

    ;
}
