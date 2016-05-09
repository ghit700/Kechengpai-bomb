package com.ketangpai.modelImpl;

import android.content.Context;
import android.util.Log;

import com.ketangpai.callback.ResultsCallback;
import com.ketangpai.bean.Notice;
import com.ketangpai.fragment.CourseTabFragment;
import com.ketangpai.model.NoticeModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by nan on 2016/4/22.
 */
public class NoticeModelImpl implements NoticeModel {




    @Override
    public void getNoticeList(Context context, int c_id, final ResultsCallback resultsCallback) {
        Log.i(CourseTabFragment.TAG, "getNoticeList c_id=" + c_id);
        String sql = "select * from Notice where c_id=?";
        BmobQuery<Notice> bmobQuery = new BmobQuery<Notice>();
        bmobQuery.doSQLQuery(context, sql, new SQLQueryListener<Notice>() {
            @Override
            public void done(BmobQueryResult<Notice> bmobQueryResult, BmobException e) {
                List list = bmobQueryResult.getResults();
                if (null != list) {
                    resultsCallback.onSuccess(list);
                } else {
                    Log.i(CourseTabFragment.TAG, "getNoticeList null");

                }
            }
        }, c_id);

    }

    @Override
    public void getNoticeListToStudent(Context context, int c_id, long add_time, final ResultsCallback resultsCallback) {
        String sql = "select * from Notice where c_id=? and time>?";
        BmobQuery<Notice> bmobQuery = new BmobQuery<Notice>();
        bmobQuery.doSQLQuery(context, sql, new SQLQueryListener<Notice>() {
            @Override
            public void done(BmobQueryResult<Notice> bmobQueryResult, BmobException e) {
                List list = bmobQueryResult.getResults();
                if (null != list) {
                    resultsCallback.onSuccess(list);
                } else {
                    Log.i(CourseTabFragment.TAG, "getNoticeList null");

                }
            }
        }, c_id, add_time);
    }
}
