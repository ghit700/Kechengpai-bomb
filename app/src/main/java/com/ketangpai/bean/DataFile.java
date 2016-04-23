package com.ketangpai.bean;

/**
 * 资料文件
 * Created by nan on 2016/4/22.
 */
public class DataFile {
    private String name;
    private String size;

    public DataFile(String name, String size) {
        this.name = name;
        this.size = size;
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
