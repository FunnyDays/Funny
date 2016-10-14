package com.carpediem.vv.funny.bean.MusicBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/9/22.
 */

public class MusicLink extends BmobObject {
    public String getMusicLink() {
        return musicLink;
    }

    public void setMusicLink(String musicLink) {
        this.musicLink = musicLink;
    }

    private String musicLink;
}
