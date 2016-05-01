package com.ketangpai.viewInterface;

import com.ketangpai.bean.Data;
import com.ketangpai.bean.Student_Homework;

/**
 * Created by nan on 2016/5/1.
 */
public interface SHomeworkViewInterface {
    void uploadAttachOnComplete(Data data);

    void onProgress(int value);

    void getStudentHomeworkOnComplete(Student_Homework student_homework);

}
