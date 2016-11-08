package com.carpediem.vv.funny.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.carpediem.vv.funny.Activity.MainActivity;
import com.carpediem.vv.funny.Adapter.TabsViewPagerAdapter;
import com.carpediem.vv.funny.Base.BaseFragment;
import com.carpediem.vv.funny.R;

/**
 * Created by Administrator on 2016/11/4.
 */

public class AllTabFragment extends BaseFragment {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static AllTabFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        AllTabFragment fragment = new AllTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initView() {
        Log.e("weiwei","AllTabFragment_initView");
        View view = View.inflate(mActivity, R.layout.fragment_all_tab, null);
        //toolbar设置
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("FunDays");
        toolbar.setNavigationIcon(R.drawable.ic_book_white_24dp);
        //tablayout设置
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorControlNormal));
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);


        //viewpager适配器
        TabsViewPagerAdapter adapter = new TabsViewPagerAdapter(((MainActivity)mActivity).getSupportFragmentManager());
        adapter.addFragment(new DailyFragment(), "今日");
        adapter.addFragment(new MovieFragment(), "电影");
        adapter.addFragment(new GameFragment(), "游戏");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void initData() {
        Log.e("weiwei","AllTabFragment_initData");
    }
}
