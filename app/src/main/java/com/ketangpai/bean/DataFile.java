package com.ketangpai.bean;

import java.io.Serializable;

/**
 * 资料文件
 * Created by nan on 2016/4/22.
 */
public class DataFile implements Serializable {
    private String name;
    private String size;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DataFile(String name, String size) {
        this.name = name;
        this.size = size;
    }

    public DataFile(String name, String size, String url) {
        this.name = name;
        this.size = size;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
