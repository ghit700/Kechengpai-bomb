package com.ketangpai.bean;

import java.util.List;

/**
 * Created by nan on 2016/4/9.
 */
public class Notification_message {
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    private List<String> courses;

    public int getCount() {
        return courses == null ? 1 : 1 + courses.size();
    }


}
