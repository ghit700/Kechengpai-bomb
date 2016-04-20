package com.ketangpai.utils;

import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by nan on 2016/4/10.
 */
public class FileUtils {
    private final static String[] imageFormat = new String[]{"jpg", "png", ".bmp", "jpeg", "gif"};
    private final static String[] videoFormat = new String[]{"mp4", "avi", "3gp", "rmvb", "mov", "wmv", "fly"};
    private final static String[] documentFormat = new String[]{"txt", "pdf", "xlsx", "doc", "ppt", "pptx", "wps", "docx", "xls"};

    public final static int TYPE_FILE_IMAGE = 0;
    public final static int TYPE_FILE_VIDEO = 1;
    public final static int TYPE_FILE_DOCUMENT = 2;

    /**
     * 获取文件的名字
     *
     * @param uri
     * @return
     */
    public static String getFileName(Uri uri) {
        String fileName = null;
        String[] path = uri.getPath().split("/");
        fileName = path[path.length - 1];
        return fileName;
    }



    /**
     * 获取文件的大小，单位为MB
     *
     * @param uri
     * @return
     */
    public static String getFileSize(Uri uri) {
        File file = new File(uri.getPath());
        long length = file.length();
        double size = length / (double) 1024 / (double) 1024;

        DecimalFormat df = new DecimalFormat("#.00");

        if (size < 1) {
            return 0 + df.format(size);
        }

        return df.format(size);

    }

    /**
     * 获取文件的类型
     *
     * @param fileName
     * @return
     */
    public static int getFileType(String fileName) {
        String type = fileName.substring(fileName.lastIndexOf(".") + 1);
        for (String format : documentFormat) {
            if (format.equals(type)) {
                return TYPE_FILE_DOCUMENT;
            }

        }

        for (String format : imageFormat) {
            if (format.equals(type)) {
                return TYPE_FILE_IMAGE;
            }

        }

        for (String format : videoFormat) {
            if (format.equals(type)) {
                return TYPE_FILE_VIDEO;
            }

        }
        return -1;
    }

}
