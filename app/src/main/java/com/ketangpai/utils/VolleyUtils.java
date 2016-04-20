package com.ketangpai.utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Created by nan on 2016/4/16.
 */
public class VolleyUtils {
    private static RequestQueue requestQueue;


    /**
     * 网络请求回调接口
     */
    public interface ResultCallback {
        void onSuccess(String result);

        void onError(String error);
    }


    /**
     * 单例模式 请求队列
     *
     * @return
     */
    private static RequestQueue getInstance() {
        requestQueue = Volley.newRequestQueue(AppContextUtils.getInstance());
        return requestQueue;
    }


    /**
     * get 方法
     *
     * @param url
     * @param tag
     * @param resultCallback
     */
    public static void get(String url, String tag, final ResultCallback resultCallback) {
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                resultCallback.onSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                resultCallback.onError(volleyError.getMessage());
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setTag(tag);
        getInstance().add(stringRequest);
    }

    /**
     * post 方法
     *
     * @param url
     * @param tag
     * @param params
     * @param resultCallback
     */
    public static void post(String url, String tag, final Map<String, String> params, final ResultCallback resultCallback) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                resultCallback.onSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                resultCallback.onError(volleyError.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setTag(tag);
        getInstance().add(stringRequest);
    }

    /**
     * 取消tag的http网络请求
     *
     * @param tag
     */
    public static void disposeConnect(String tag) {
        getInstance().cancelAll(tag);
    }


}
