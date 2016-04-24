package com.ketangpai.model;

import android.content.Context;

import com.ketangpai.Callback.ResultCallback;
import com.ketangpai.Callback.ResultsCallback;
import com.ketangpai.Callback.UploadFileResultCallback;
import com.ketangpai.bean.Data;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by nan on 2016/4/24.
 */
public interface DataModel {
    void uploadData(Context context, Data data, UploadFileResultCallback resultCallBack);

    void getDataList(Context context, int c_id, ResultsCallback resultsCallback);
}
