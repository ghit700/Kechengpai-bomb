package com.ketangpai.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by nan on 2016/4/22.
 */
public class Notice extends BmobObject implements Serializable{
    private Integer c_id;
    private String title;
    private String content;
    private long time;
    private String paths;


    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }
}
