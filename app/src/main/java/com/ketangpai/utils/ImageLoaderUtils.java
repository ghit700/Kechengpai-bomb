package com.ketangpai.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
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
        Glide.with(context).load(url).placeholder(R.drawable.da8e974dc_r).centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.da8e974dc_r).crossFade().into(imageView);
    }

    public static void displayNoDisk(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(url).
                placeholder(R.drawable.da8e974dc_r).
                skipMemoryCache(true).centerCrop().
                diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.da8e974dc_r).
                crossFade().
                into(imageView);
    }


}
