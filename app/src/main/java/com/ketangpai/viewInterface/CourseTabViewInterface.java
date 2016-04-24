package com.ketangpai.viewInterface;

import com.ketangpai.bean.Homework;
import com.ketangpai.bean.Notice;

import java.util.List;

/**
 * Created by nan on 2016/4/24.
 */
public interface CourseTabViewInterface {
    void getHomeworkListOnComplete(List<Homework> homeworks);

    void uploadOnProgress(int value);

    void uploadDataOnComplete(String url);

    void getDataListOnComplete(List datas);

    void getNoticeListOnComplete(List<Notice> notices);

    void getExamkListOnComplete(List exams);
}
