package com.carpediem.vv.funny.Base;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.carpediem.vv.funny.R;


/**
 * 四个页面的基类
 * Created by Administrator on 2015/11/16.
 */
public class BasePager {


    public final View mRootView;

    protected Context mActivity;

    public BasePager(Activity activity) {
        this.mActivity = activity;
        mRootView = initView();

    }

    public View initView() {

        View view = View.inflate(mActivity, R.layout.base_paper, null);

        return view;
    }



    /**
     * 初始化数据, 子类覆盖此方法, 实现自己的数据初始化
     */
    public void initData() {
        Toast.makeText(mActivity,"jichgeng",Toast.LENGTH_SHORT).show();
    }


}
