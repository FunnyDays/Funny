package com.carpediem.vv.funny;

import android.app.Application;


import com.carpediem.vv.funny.Utils.Loading.LoadingLayout;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

/**
 * Created by Administrator on 2016/11/28.
 */

public class App extends Application {
    {
        Config.REDIRECT_URL = "http://www.qsztx.com";
        //微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin("wxb62478d5d6955e1c", "20a2c4b2342614f9cc69c88b0d8b72d7");
        //新浪微博
        PlatformConfig.setSinaWeibo("4227802417", "648d630d8f9126172e64077c6bf383be");
        //QQ、QZone
        PlatformConfig.setQQZone("1105437477", "VFjUid5UIhMt9FoD");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        //分享初始化
        UMShareAPI.get(this);
        //空布局初始化
        initLoadView();
        //下载初始化
        x.Ext.init(this);
    }
    private void initLoadView() {
        LoadingLayout.getConfig()
                .setErrorText("出错啦~请稍后重试！")
                .setEmptyText("抱歉，暂无数据")
                .setNoNetworkText("无网络连接，请检查您的网络···")
                .setErrorImage(R.mipmap.error)
                .setEmptyImage(R.mipmap.empty)
                .setNoNetworkImage(R.mipmap.no_network)
                .setAllTipTextColor(R.color.colorAccent)
                .setAllTipTextSize(14)
                .setReloadButtonText("点我重试哦")
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(R.color.colorAccent)
                .setReloadButtonWidthAndHeight(150,40);
    }

}
