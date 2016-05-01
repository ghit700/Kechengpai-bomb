package com.ketangpai.presenter;

import android.content.Context;
import android.util.Log;

import com.ketangpai.base.BasePresenter;
import com.ketangpai.bean.Student_Homework;
import com.ketangpai.model.HomeworkModel;
import com.ketangpai.modelImpl.HomeworkModelImpl;
import com.ketangpai.viewInterface.THomeworkDetailViewInterface;
import com.ketangpai.viewInterface.THomeworkViewInterface;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by nan on 2016/5/1.
 */
public class THomeworkDetailPresenter extends BasePresenter<THomeworkDetailViewInterface> {
    private THomeworkDetailViewInterface mTHomeworkViewInterface;
    private HomeworkModel mHomeworkModel;

    public THomeworkDetailPresenter() {
        mHomeworkModel = new HomeworkModelImpl();
    }

    public void correctHomework(Context context, Student_Homework homework) {
        if (isViewAttached()) {
            mTHomeworkViewInterface = getView();
            mHomeworkModel.correctHomework(context, homework, new UpdateListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(int i, String s) {
                    Log.i("====", "correctHomework  " + s);
                }
            });
        }
    }

    ;
}
