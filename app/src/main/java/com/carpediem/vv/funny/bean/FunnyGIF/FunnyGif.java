package com.carpediem.vv.funny.bean.FunnyGIF;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/7/30.
 */
public class FunnyGif extends BmobObject {
    private String textContent;
    private BmobFile gifContent;
    private String  publishTime;
    private String  textJoke;
    public String getTextJoke() {
        return textJoke;
    }

    public void setTextJoke(String textJoke) {
        this.textJoke = textJoke;
    }



    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }


    public BmobFile getGifContent() {
        return gifContent;
    }

    public void setGifContent(BmobFile gifContent) {
        this.gifContent = gifContent;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }


}
