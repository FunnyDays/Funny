package com.carpediem.vv.funny.bean.downLoad;

/**
 * Created by Administrator on 2016/12/1.
 */

public class ThreadInfo {

    private int fileId;
    private int id;
    private String url;
    private String  name;
    private String  icon;
    private long start;
    private long end;
    private long finished;
    private long length;
    private int pause;
    public int getPause() {
        return pause;
    }

    public void setPause(int pause) {
        this.pause = pause;
    }
    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "fileId=" + fileId +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name=" + name +
                ", icon=" + icon +
                ", start=" + start +
                ", end=" + end +
                ", finished=" + finished +
                ", pause=" + pause +
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

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }

    public ThreadInfo(int fileId,int id, String url, String name,String icon, long start, long stop, long finished,int pause) {

        this.fileId = fileId;
        this.id = id;
        this.url = url;
        this.name = name;
        this.icon = icon;
        this.start = start;
        this.end = stop;
        this.finished = finished;
        this.pause = pause;
    }

    public ThreadInfo() {

    }
}
