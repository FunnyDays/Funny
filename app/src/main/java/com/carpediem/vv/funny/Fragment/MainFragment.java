package com.carpediem.vv.funny.Fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.carpediem.vv.funny.Base.BaseFragment;
import com.carpediem.vv.funny.Base.BasePager;
import com.carpediem.vv.funny.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/28.
 */
public class MainFragment extends BaseFragment implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener         {


    public ArrayList<BasePager> arrayList;
    public ViewPager vpContentFragment;
    private RadioGroup radioGroup;
    private RadioButton rbGame;
    private RadioButton rbDaily;
    private RadioButton rbKnow;
    private RadioButton rbMy;

    @Override
    protected View initView() {

        View view = View.inflate(mActivity, R.layout.fragment_main, null);
        vpContentFragment = (ViewPager) view.findViewById(R.id.vp_content_fragment);
        radioGroup = (RadioGroup) view.findViewById(R.id.rg_content_fragment);
        rbGame = (RadioButton) view.findViewById(R.id.rb_main_fragment_game);
        rbDaily = (RadioButton) view.findViewById(R.id.rb_main_fragment_daily);
        rbKnow = (RadioButton) view.findViewById(R.id.rb_main_fragment_knowledge);
        rbMy = (RadioButton) view.findViewById(R.id.rb_main_fragment_my);
        // 初始化ViewPager的数据
        arrayList = new ArrayList<>();

        arrayList.add(new TestFragment(mActivity));
        arrayList.add(new TestFragment(mActivity));
        arrayList.add(new TestFragment(mActivity));
        //绑定数据
        ContentAdapter contentAdapter = new ContentAdapter();
        vpContentFragment.setAdapter(contentAdapter);
        vpContentFragment.addOnPageChangeListener(this);
        //监听单选按钮组中的按钮选中变化
        radioGroup.setOnCheckedChangeListener(this);
        //设置默认选中的页面为: 首页
        radioGroup.check(R.id.rb_main_fragment_daily);
        return view;
    }

    @Override
    public void initData() {

        //加载首页数据
       // arrayList.get(0).initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //会刷新两次，出问题

            arrayList.get(position).initData(); // 把当前选中的页面的数据加载了


    }

    @Override
    public void onPageSelected(int position) {
        //把页签也替换了
        if (position == 0) {
            rbDaily.setChecked(true);
            rbGame.setChecked(false);
            rbKnow.setChecked(false);
            rbMy.setChecked(false);
        }else if (position == 1){
            rbDaily.setChecked(false);
            rbGame.setChecked(true);
            rbKnow.setChecked(false);
            rbMy.setChecked(false);
        }else if (position == 2){
            rbDaily.setChecked(false);
            rbGame.setChecked(false);
            rbKnow.setChecked(true);
            rbMy.setChecked(false);
        }else if (position == 3){
            rbDaily.setChecked(false);
            rbGame.setChecked(false);
            rbKnow.setChecked(false);
            rbMy.setChecked(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_main_fragment_daily:
                vpContentFragment.setCurrentItem(0);
                break;
            case R.id.rb_main_fragment_game:
                vpContentFragment.setCurrentItem(1);
                break;
            case R.id.rb_main_fragment_knowledge:
                vpContentFragment.setCurrentItem(2);
                break;
            case R.id.rb_main_fragment_my:
                vpContentFragment.setCurrentItem(3);
                break;

        }
    }

     class ContentAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 把对应pagerList集合中的position位置的页面的布局添加到ViewPager中, 并返回
            BasePager basePager = arrayList.get(position);
            container.addView(basePager.mRootView); // 把布局添加到ViewPager中
            // pager.initData(); // 初始化数据: 不能在这里加载, 此方法会预加载下一个页面, 加载下一个页面的数据, 会浪费用户的流量
            return basePager.mRootView;

        }
    }

}
