package com.carpediem.vv.funny.bean.MovieBean;

import java.io.Serializable;

/**
 * Created by LG on 2016/11/27.
 */

public class DownloadInfo implements Serializable {
    private String url;
    private String name;

    public DownloadInfo() {
    }

    public DownloadInfo(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
