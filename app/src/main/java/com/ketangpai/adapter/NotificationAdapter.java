package com.ketangpai.adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.bean.Notification_message;
import com.ketangpai.constant.Constant;
import com.ketangpai.nan.ketangpai.R;

import java.util.List;
import java.util.Objects;

/**
 * Created by nan on 2016/3/21.
 */
public class NotificationAdapter extends BaseAdapter {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CONTENT = 1;

    private SparseArray mKeyedSections;


    public NotificationAdapter(Context mContext, List mDataList) {
        super(mContext, mDataList);
        mKeyedSections=new SparseArray();
        reorderSections();
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (Object messagme :
                mDataList) {
            count += ((Notification_message) messagme).getCount();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderAtPositon(position)) {
            return TYPE_HEADER;
        }
        return TYPE_CONTENT;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if (TYPE_HEADER == viewType) {
            return R.layout.item_notification_time;
        }
        return R.layout.item_notification_content;
    }

    @Override
    public void addItem(int positon, Object item) {
        super.addItem(positon, item);
        reorderSections();
        notifyItemChanged(positon);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object object=new Object();
        bindData(holder,position,object);

    }

    @Override
    protected void bindData(ViewHolder holder, final int position, Object item) {
        if(getItemViewType(position)==TYPE_HEADER){

            TextView time = (TextView) holder.getViewById(R.id.tv_notification_time);

        }else{
            TextView courseName = (TextView) holder.getViewById(R.id.item_notification_courseName);
            TextView content = (TextView) holder.getViewById(R.id.item_notification_content);
            LinearLayout ll_notification = (LinearLayout) holder.getViewById(R.id.ll_notification);

            ll_notification.setBackgroundResource(typedValue.resourceId);

            ll_notification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getOnItemClickListener().onItemClick(v, position);
                }
            });
        }


    }

    public boolean isHeaderAtPositon(int positon) {
        for (int i = 0; i <mKeyedSections.size() ; ++i) {
            if(positon==mKeyedSections.keyAt(i)){
                return true;
            }
        }
        return false;
    }


    private void reorderSections() {
        mKeyedSections.clear();
        int startPosition = 0;
        for (Object message : mDataList) {
            mKeyedSections.put(startPosition, message);
            startPosition += ((Notification_message) message).getCount();
        }
    }
}
