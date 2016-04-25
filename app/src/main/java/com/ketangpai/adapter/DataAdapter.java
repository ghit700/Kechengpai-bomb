package com.ketangpai.adapter;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.bean.Data;
import com.ketangpai.nan.ketangpai.R;

import java.util.List;

/**
 * Created by nan on 2016/3/27.
 */
public class DataAdapter extends BaseAdapter<Data> {

    public DataAdapter(Context mContext, List mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_add_homework_data;
    }

    @Override
    protected void bindData(ViewHolder holder, int position, Data item) {
        TextView fileName = (TextView) holder.getViewById(R.id.tv_data_fileName);
        TextView tv_data_fileSize = (TextView) holder.getViewById(R.id.tv_data_fileSize);

        holder.itemView.setBackgroundResource(typedValue.resourceId);
        fileName.setText(item.getName());
        tv_data_fileSize.setText(item.getSize() + "MB");


    }

}
