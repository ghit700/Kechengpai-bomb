package com.ketangpai.Callback;

/**
 * 返回的是对象
 * Created by nan on 2016/4/21.
 */
public interface AttachmentResultCallback {
    void onSuccess(String fileUrl);

    void onProgress(Integer value);

    void onFailure(String e);
}
