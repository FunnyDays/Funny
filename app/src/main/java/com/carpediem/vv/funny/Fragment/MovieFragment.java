package com.carpediem.vv.funny.Fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.carpediem.vv.funny.Base.BaseFragment;
import com.carpediem.vv.funny.R;

/**
 * Created by Administrator on 2016/11/8.
 */
public class MovieFragment extends BaseFragment {
    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_movie, null);
        return view;
    }
}
