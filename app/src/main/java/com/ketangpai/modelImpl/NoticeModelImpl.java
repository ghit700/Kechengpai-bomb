package com.ketangpai.modelImpl;

import android.content.Context;
import android.util.Log;

import com.ketangpai.Callback.AttachmentResultCallback;
import com.ketangpai.Callback.ResultCallback;
import com.ketangpai.bean.Notice;
import com.ketangpai.fragment.AddNoticeFragment;
import com.ketangpai.model.NoticeModel;
import com.ketangpai.utils.FileUtils;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by nan on 2016/4/22.
 */
public class NoticeModelImpl implements NoticeModel {



    @Override
    public void publishNotice(Context context, Notice notice, SaveListener resultCallback) {
        Log.i(AddNoticeFragment.TAG, "publishNotice c_id=" + notice.getC_id() + " title=" + notice.getTitle() +
                " content=" + notice.getContent() );
        notice.save(context, resultCallback);
    }
}
