package com.ketangpai.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ketangpai.nan.ketangpai.R;

import java.io.File;

/**
 * Created by nan on 2016/4/16.
 */
public class ImageLoaderUtils {
    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }

        Glide.with(context).load(url).placeholder(placeholder)
                .error(error).crossFade().into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url).placeholder(R.drawable.da8e974dc_r)
                .error(R.drawable.da8e974dc_r).crossFade().into(imageView);
    }


    public static void displayFile(Context context, ImageView imageView, File file) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Log.i("====","111");
        Glide.with(context).load(file).placeholder(R.drawable.da8e974dc_r)
                .error(R.drawable.da8e974dc_r).crossFade().into(imageView);
    }

}
