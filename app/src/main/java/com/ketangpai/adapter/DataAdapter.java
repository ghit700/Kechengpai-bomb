package com.ketangpai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.bean.DataFile;
import com.ketangpai.bean.DocumentFile;
import com.ketangpai.nan.ketangpai.R;

import java.io.File;
import java.util.List;

/**
 * Created by nan on 2016/3/27.
 */
public class DataAdapter extends BaseAdapter<DataFile> {

    public DataAdapter(Context mContext, List mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_add_homework_data;
    }

    @Override
    protected void bindData(ViewHolder holder, int position, DataFile item) {
        TextView fileName = (TextView) holder.getViewById(R.id.tv_data_fileName);
        TextView fileSize = (TextView) holder.getViewById(R.id.tv_data_fileSize);
        TextView tv_data_progress = (TextView) holder.getViewById(R.id.tv_data_progess);
        ProgressBar pb_data = (ProgressBar) holder.getViewById(R.id.pb_data);

        holder.itemView.setBackgroundResource(typedValue.resourceId);
        fileName.setText(item.getName());
        fileSize.setText(item.getSize() + "MB");

    }

}
