package com.ketangpai.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.ketangpai.base.BaseFragment;
import com.ketangpai.fragment.CourseTabFragment;
import com.ketangpai.nan.ketangpai.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nan on 2016/3/16.
 */
public class CourseTabsPagerAdapter extends FragmentPagerAdapter {

    private String[] mCourseTabs;
    private int c_id;

    public CourseTabsPagerAdapter(FragmentManager fm, Context context, int c_id) {
        super(fm);
        mCourseTabs = context.getResources().getStringArray(R.array.course_tabs);
        this.c_id = c_id;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mCourseTabs[position];
    }


    @Override
    public int getCount() {
        return mCourseTabs.length;
    }


    @Override
    public Fragment getItem(int position) {

        return CourseTabFragment.newInstance(position, c_id);
    }


}
