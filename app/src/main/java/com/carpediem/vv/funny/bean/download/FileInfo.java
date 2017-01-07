package com.carpediem.vv.funny.bean.download;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/1.
 */

public class FileInfo implements Serializable {
    private int id;
    private String url;
    private String icon;
    private String fileName;
    private int length;
    private int finished;

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", fileName='" + fileName + '\'' +
                ", length=" + length +
                ", finished=" + finished +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public FileInfo() {
    }

    public FileInfo(int id, String url,String icon, String fileName, int length, int finished) {
        this.id = id;
        this.url = url;
        this.icon = icon;
        this.fileName = fileName;
        this.length = length;
        this.finished = finished;
    }
}
