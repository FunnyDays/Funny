package com.carpediem.vv.funny.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by LG on 2016/11/27.
 */

public class MainVPAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private List<String> keySet;
    public MainVPAdapter(FragmentManager fm, List<Fragment> fragments, List<String> keySet) {
        super(fm);
        mFragments = fragments;
        this.keySet = keySet;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return keySet.get(position);
    }
}
