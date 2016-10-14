package com.carpediem.vv.funny.bean.VideoBean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/9/22.
 */

public class VideoBean extends BmobObject {
    public BmobFile getVideoImage() {
        return videoImage;
    }

    public void setVideoImage(BmobFile videoImage) {
        this.videoImage = videoImage;
    }

    public BmobFile getVideoFile() {
        return videoFile;
    }

    public void setVideoFile(BmobFile videoFile) {
        this.videoFile = videoFile;
    }

    private BmobFile videoImage;
    private BmobFile videoFile;



}
