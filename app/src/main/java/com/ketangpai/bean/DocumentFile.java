package com.ketangpai.bean;

import com.ketangpai.constant.Constant;

import java.io.File;

/**
 * 文档文件
 * Created by Administrator on 2016/4/14.
 */
public class DocumentFile {

    /**
     * 文件的类型
     */
    private int type;
    /**
     * 文件的名字
     */
    private String name;
    /**
     * 文件的大小
     */
    private String size;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;

    public DocumentFile(int type, String name, String size) {
        this.type = type;
        this.name = name;
        this.size = size;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

}
