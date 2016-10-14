package com.carpediem.vv.funny.bean.FunnyGIF;

import com.carpediem.vv.funny.bean.Userbean.MyUser;
import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/9/1.
 */
public class Comment extends BmobObject {

    private String content;//评论内容

    private MyUser user;//评论的用户，Pointer类型，一对一关系

    private FunnyGif funnyGif; //所评论的帖子，这里体现的是一对多的关系，一个评论只能属于一个微博

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public FunnyGif getFunnyGif() {
        return funnyGif;
    }

    public void setFunnyGif(FunnyGif funnyGif) {
        this.funnyGif = funnyGif;
    }
}
