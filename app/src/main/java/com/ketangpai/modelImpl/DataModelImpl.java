package com.ketangpai.modelImpl;

import android.content.Context;
import android.util.Log;

import com.ketangpai.callback.ResultsCallback;
import com.ketangpai.callback.UploadFileResultCallback;
import com.ketangpai.bean.Data;
import com.ketangpai.fragment.CourseTabFragment;
import com.ketangpai.model.DataModel;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by nan on 2016/4/24.
 */
public class DataModelImpl implements DataModel {


    @Override
    public void getDataList(Context context, int c_id, final ResultsCallback resultsCallback) {
        Log.i(CourseTabFragment.TAG, "getDataList c_id=" + c_id);
        String sql = "select * from Data where c_id=?";
        BmobQuery<Data> query = new BmobQuery<Data>();
        query.doSQLQuery(context, sql, new SQLQueryListener<Data>() {
            @Override
            public void done(BmobQueryResult<Data> bmobQueryResult, BmobException e) {
                List list = bmobQueryResult.getResults();
                if (null != list) {
                    Log.i(CourseTabFragment.TAG, list.size() + "  ");
                    resultsCallback.onSuccess(list);
                } else {
                    resultsCallback.onFailure(e);
                }
            }
        }, c_id);
    }
}
