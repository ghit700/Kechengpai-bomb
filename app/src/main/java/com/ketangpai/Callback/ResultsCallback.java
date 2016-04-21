package com.ketangpai.Callback;

import android.util.Log;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.exception.BmobException;

/**
 * 返回的是数组
 * Created by nan on 2016/4/21.
 */
public interface ResultsCallback {
     void onSuccess(List list);

    void onFailure(BmobException e)

            ;
}
