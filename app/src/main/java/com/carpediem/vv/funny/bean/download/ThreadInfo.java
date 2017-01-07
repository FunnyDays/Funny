package com.carpediem.vv.funny.bean.download;

/**
 * Created by Administrator on 2016/12/1.
 */

public class ThreadInfo {
    private int id;
    private String url;


    private String icon;
    private String name;
    private long start;
    private long end;
    private long finished;
    private long length;

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }


    @Override
    public String toString() {
        return "ThreadInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", start=" + start +
                ", end=" + end +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ThreadInfo(int id, String url,String icon,String name, long start, long stop, long finished) {

        this.id = id;
        this.url = url;
        this.icon = icon;
        this.name = name;
        this.start = start;
        this.end = stop;
        this.finished = finished;
    }

    public ThreadInfo() {

    }
}
