package com.carpediem.vv.funny.bean;

/**
 * Created by Administrator on 2016/12/18.
 */
public class SearchBean {
    String name;
    String time;

    public SearchBean(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
