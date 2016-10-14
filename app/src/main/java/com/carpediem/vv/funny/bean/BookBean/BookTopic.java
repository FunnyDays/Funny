package com.carpediem.vv.funny.bean.BookBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/9/7.
 */
public class BookTopic extends BmobObject {

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    private  String topicName;
}
