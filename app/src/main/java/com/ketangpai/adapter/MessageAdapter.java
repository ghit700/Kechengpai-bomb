package com.ketangpai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.nan.ketangpai.R;

import java.util.List;


/**
 * Created by nan on 2016/4/17.
 */
public class MessageAdapter extends BaseAdapter {

    public MessageAdapter(Context mContext, List mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_message;
    }

    @Override
    protected void bindData(ViewHolder holder, int position, Object item) {
        ImageView img_message = (ImageView) holder.getViewById(R.id.img_message);
        TextView tv_message_name = (TextView) holder.getViewById(R.id.tv_message_name);
        TextView tv_message_time = (TextView) holder.getViewById(R.id.tv_message_time);
        TextView tv_message_content = (TextView) holder.getViewById(R.id.tv_message_content);

        holder.itemView.setBackgroundResource(typedValue.resourceId);


    }

   
}
