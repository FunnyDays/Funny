package com.carpediem.vv.funny.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.carpediem.vv.funny.Adapter.MainVPAdapter;
import com.carpediem.vv.funny.Base.BaseFragment;
import com.carpediem.vv.funny.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by LG on 2016/11/29.
 */

public class LYMoviesFragment extends BaseFragment{
    private TabLayout mTab;
    private Toolbar mToolbar;
    private ViewPager mContent;
    private List<Fragment> mFragments;
    public Map<String, Integer> mClasses = new HashMap<String, Integer>();
    private List<String> mTitles;
    private boolean isHide;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lymovies, null);
    }

    @Override
    protected View initView() {
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        minitView();
        minitData();
    }

    private void minitView() {
        mToolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        mTab = (TabLayout) getView().findViewById(R.id.tab);
        mContent = (ViewPager) getView().findViewById(R.id.content);
    }

    private void minitData() {
        mFragments = new ArrayList<Fragment>();
        mTitles = new ArrayList<String>();
        mClasses.put("大陆剧", 15);
        mClasses.put("港台剧", 16);
        mClasses.put("欧美剧", 17);
        mClasses.put("日韩剧", 18);
        mClasses.put("新马泰", 19);
        mClasses.put("动作片", 8);
        mClasses.put("喜剧片", 9);
        mClasses.put("爱情片", 10);
        mClasses.put("科幻片", 11);
        mClasses.put("恐怖片", 12);
        mClasses.put("剧情片", 13);
        mClasses.put("战争片", 14);
        mClasses.put("其他片", 7);
        Set<String> keySet = mClasses.keySet();
        for (String name : keySet) {
            int type = mClasses.get(name);
            SubMoviesClassFragment hf = new SubMoviesClassFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id", type);
            hf.setArguments(bundle);
            mFragments.add(hf);
            mTab.addTab(mTab.newTab().setText(name));
            mTitles.add(name);
        }
        mContent.setAdapter(new MainVPAdapter(getChildFragmentManager(), mFragments, mTitles));
        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTab.setupWithViewPager(mContent);
    }
}
