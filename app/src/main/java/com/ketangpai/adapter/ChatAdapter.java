package com.ketangpai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.nan.ketangpai.R;

import java.util.List;


/**
 * Created by nan on 2016/3/21.
 */
public class ChatAdapter extends BaseAdapter {

    public ChatAdapter(Context mContext, List mDataList) {
        super(mContext, mDataList);
    }

    @Override
    public int getItemViewType(int position) {
        return (int) mDataList.get(position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public void addItem(int positon, Object item) {
        super.addItem(positon, item);
    }

    @Override
    public void deleteItem(int positon) {
        super.deleteItem(positon);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        if (viewType == 0) {
            return R.layout.item_chat_acceptor;
        } else {
            return R.layout.item_chat_receiver;
        }

    }

    @Override
    protected void bindData(ViewHolder holder, int position, Object item) {
        //初始化view
        ImageView UserImg;
        TextView text;
        if(getItemViewType(position)==0){
             UserImg= (ImageView) holder.getViewById(R.id.img_chat_acceptor_userIcon);
             text= (TextView) holder.getViewById(R.id.tv_chat_acceptor_text);
        }else{
            UserImg= (ImageView) holder.getViewById(R.id.img_chat_receiver_userIcon);
            text= (TextView) holder.getViewById(R.id.tv_chat_receiver_text);
        }

        //初始化view的值

    }


}
