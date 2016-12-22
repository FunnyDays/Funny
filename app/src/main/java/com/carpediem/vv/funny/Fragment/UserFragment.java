package com.carpediem.vv.funny.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.carpediem.vv.funny.Activity.ManagerAppActivity;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.Utils.IntentUtils;

/**
 * Created by Administrator on 2016/11/8.
 */
public class UserFragment extends com.carpediem.vv.funny.Base.BaseFragment implements View.OnClickListener {

    private LinearLayout mLlAppManager;

    public static UserFragment newInstance() {
        Bundle args = new Bundle();
        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_user, null);
        mLlAppManager = (LinearLayout) view.findViewById(R.id.ll_app_manager);
        mLlAppManager.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_app_manager:
                IntentUtils.startActivity(mActivity,ManagerAppActivity.class);
                break;
        }
    }
}
