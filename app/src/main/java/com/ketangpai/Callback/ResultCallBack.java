package com.ketangpai.Callback;

import java.util.List;
import java.util.Objects;

import cn.bmob.v3.exception.BmobException;

/**
 * 返回的是对象
 * Created by nan on 2016/4/21.
 */
public interface ResultCallback {
    void onSuccess(Object object);

    void onFailure(String e);
}
