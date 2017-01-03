package com.carpediem.vv.funny.bean.downLoad;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/1.
 */

public class FileInfo  implements Serializable {
    private int id;
    private String url;
    private String fileName;
    private String fileIcon;
    private int length;
    private int finished;

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileIcon='" + fileIcon + '\'' +
                ", length=" + length +
                ", finished=" + finished +
                '}';
    }
    public String getFileIcon() {
        return fileIcon;
    }

    public void setFileIcon(String fileIcon) {
        this.fileIcon = fileIcon;
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

    public FileInfo(int id, String url, String fileName, String fileIcon, int length, int finished) {
        this.id = id;
        this.url = url;
        this.fileName = fileName;
        this.fileIcon = fileIcon;
        this.length = length;
        this.finished = finished;
    }
}
