package com.ketangpai.modelImpl;

import android.content.Context;
import android.util.Log;

import com.ketangpai.Callback.AttachmentResultCallback;
import com.ketangpai.Callback.ResultCallback;
import com.ketangpai.Callback.ResultsCallback;
import com.ketangpai.bean.Notice;
import com.ketangpai.fragment.AddNoticeFragment;
import com.ketangpai.fragment.CourseTabFragment;
import com.ketangpai.model.NoticeModel;
import com.ketangpai.utils.FileUtils;

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
 * Created by nan on 2016/4/22.
 */
public class NoticeModelImpl implements NoticeModel {


    @Override
    public void publishNotice(Context context, Notice notice, SaveListener resultCallback) {
        Log.i(AddNoticeFragment.TAG, "publishNotice c_id=" + notice.getC_id() + " title=" + notice.getTitle() +
                " content=" + notice.getContent());
        notice.save(context, resultCallback);
    }

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
}
