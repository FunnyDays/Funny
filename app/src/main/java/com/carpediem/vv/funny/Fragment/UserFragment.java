package com.carpediem.vv.funny.Fragment;

import android.view.View;

import com.carpediem.vv.funny.R;

/**
 * Created by Administrator on 2016/11/8.
 */
public class UserFragment extends com.carpediem.vv.funny.Base.BaseFragment {
    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_user, null);
        return view;
    }
}
