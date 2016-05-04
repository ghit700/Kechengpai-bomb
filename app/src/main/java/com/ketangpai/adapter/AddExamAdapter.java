package com.ketangpai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.ketangpai.base.BaseAdapter;
import com.ketangpai.nan.ketangpai.R;

import java.util.List;

/**
 * Created by nan on 2016/5/4.
 */
public class AddExamAdapter extends BaseAdapter {


    public AddExamAdapter(Context mContext, List mDataList) {
        super(mContext, mDataList);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.item_t_homework;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
