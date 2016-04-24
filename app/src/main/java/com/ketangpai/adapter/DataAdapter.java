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
import com.ketangpai.utils.FileUtils;

import java.io.File;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by nan on 2016/3/27.
 */
public class DataAdapter extends BaseAdapter<BmobFile> {

    public DataAdapter(Context mContext, List mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_add_homework_data;
    }

    @Override
    protected void bindData(ViewHolder holder, int position, BmobFile item) {
        TextView fileName = (TextView) holder.getViewById(R.id.tv_data_fileName);
        TextView fileSize = (TextView) holder.getViewById(R.id.tv_data_fileSize);
        TextView tv_data_progress = (TextView) holder.getViewById(R.id.tv_data_progess);
        ProgressBar pb_data = (ProgressBar) holder.getViewById(R.id.pb_data);

        holder.itemView.setBackgroundResource(typedValue.resourceId);
        fileName.setText(item.getFilename());
        fileSize.setText(FileUtils.getFileSize(item.getLocalFile().length()) + "MB");

    }

}
