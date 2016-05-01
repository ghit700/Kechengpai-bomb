package com.ketangpai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.bean.Notification;
import com.ketangpai.bean.NotificationInfo;
import com.ketangpai.listener.OnItemClickListener;
import com.ketangpai.nan.ketangpai.R;

import java.util.List;

/**
 * Created by nan on 2016/3/21.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {


    protected Context mContext;
    protected List mDataList;
    protected LayoutInflater mLayoutInflater;
    protected TypedValue typedValue;


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CONTENT = 1;

    OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private SparseArray mKeyedSections;


    public NotificationAdapter(Context mContext, List mDataList) {
        mKeyedSections = new SparseArray();
        reorderSections();
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (Object messagme : mDataList) {
            count += ((Notification) messagme).getCount();
        }
        return count;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = mLayoutInflater.inflate(getItemLayoutId(viewType), parent, false);
        typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_HEADER) {

            TextView time = (TextView) holder.getViewById(R.id.tv_notification_time);

            Notification notification = (Notification) mKeyedSections.get(position);
            time.setText(notification.getTime());

        } else {
            Log.i("===wu", "content");

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

            NotificationInfo notificationInfo = findSectionItemAtPosition(position);
            if (null != notificationInfo) {
                courseName.setText(notificationInfo.getC_name());
                content.setText(notificationInfo.getContent());
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderAtPositon(position)) {
            return TYPE_HEADER;
        }

        return TYPE_CONTENT;
    }


    protected int getItemLayoutId(int viewType) {
        if (TYPE_HEADER == viewType) {
            return R.layout.item_notification_time;
        }
        return R.layout.item_notification_content;
    }


    public boolean isHeaderAtPositon(int positon) {
        for (int i = 0; i < mKeyedSections.size(); ++i) {
            Log.i("====", mKeyedSections.keyAt(i) + "  ");
            if (positon == mKeyedSections.keyAt(i)) {
                return true;
            }
        }
        return false;
    }


    public void reorderSections() {
        mKeyedSections.clear();
        int startPosition = 0;
        for (Object message : mDataList) {
            mKeyedSections.put(startPosition, message);
            startPosition += ((Notification) message).getCount();
        }
    }


    private NotificationInfo findSectionItemAtPosition(int position) {
        int firstIndex, lastIndex;
        for (int i = 0; i < mKeyedSections.size(); ++i) {
            firstIndex = mKeyedSections.keyAt(i);
            lastIndex = firstIndex + ((Notification) mKeyedSections.valueAt(i)).getCount();
            Log.i("====", "first =" + firstIndex + "   last=" + lastIndex);
            if (position >= firstIndex && position < lastIndex) {
                int sectionPosition = position - firstIndex - 1;
                return ((Notification) mKeyedSections.valueAt(i)).getNotificationInfos().get(sectionPosition);
            }
        }
        return null;
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private SparseArray<View> mViews;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mViews = new SparseArray<View>();
        }

        public View getViewById(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return view;
        }


        @Override
        public void onClick(View v) {
            if (getOnItemClickListener() != null) {
                getOnItemClickListener().onItemClick(v, getPosition());
            }
        }


    }


}
