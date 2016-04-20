package com.ketangpai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.bean.DocumentFile;
import com.ketangpai.nan.ketangpai.R;

import java.io.File;
import java.util.List;

/**
 * Created by nan on 2016/3/27.
 */
public class DataAdapter extends BaseAdapter<DocumentFile> {

    public DataAdapter(Context mContext, List mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_add_homework_data;
    }

    @Override
    protected void bindData(ViewHolder holder, int position, DocumentFile item) {
        TextView fileName = (TextView) holder.getViewById(R.id.data_fileName);
        TextView fileSize = (TextView) holder.getViewById(R.id.data_fileSize);

        holder.itemView.setBackgroundResource(typedValue.resourceId);
        fileName.setText(item.getName());
        fileSize.setText(item.getSize() + "MB");

    }

}
