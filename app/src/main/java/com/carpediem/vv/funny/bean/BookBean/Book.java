package com.carpediem.vv.funny.bean.BookBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/9/7.
 */
public class Book extends BmobObject {
    private String bookName;
    private String bookImage;
    private String bookInfo;
    private String bookLabel;
    public String getBookLabel() {
        return bookLabel;
    }

    public void setBookLabel(String bookLabel) {
        this.bookLabel = bookLabel;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(String bookInfo) {
        this.bookInfo = bookInfo;
    }


}
