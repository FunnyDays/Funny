package com.carpediem.vv.funny.bean.MovieBean;

import java.io.Serializable;

/**
 * Created by LG on 2016/11/27.
 */

public class MovieInfo implements Serializable {
    private int _id;
    private String image;
    private String url;
    private String title;
    private String actor;
    private String area;
    private String date;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    private String detail;

    public MovieInfo() {
    }

    public MovieInfo(String image, String url, String title, String actor, String area, String date,String type) {
        this.image = image;
        this.url = url;
        this.title = title;
        this.actor = actor;
        this.area = area;
        this.date = date;
        this.type = type;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
