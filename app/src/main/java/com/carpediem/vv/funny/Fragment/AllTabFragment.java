package com.carpediem.vv.funny.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.carpediem.vv.funny.Activity.DownLoadActivity;
import com.carpediem.vv.funny.Activity.MainActivity;
import com.carpediem.vv.funny.Adapter.TabsViewPagerAdapter;
import com.carpediem.vv.funny.Base.BaseFragment;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.Utils.IntentUtils;

/**
 * Created by Administrator on 2016/11/4.
 */

public class AllTabFragment extends BaseFragment {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditText mEditText;
    private ImageButton mIbBack;
    private ImageButton mIbSearch;

    public static AllTabFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        AllTabFragment fragment = new AllTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initView() {
        Log.e("weiwei", "AllTabFragment_initView");
        View view = View.inflate(mActivity, R.layout.fragment_all_tab, null);
        //toolbar设置
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("FunDays");
        toolbar.inflateMenu(R.menu.all_tab_fragment_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_download:
                        IntentUtils.startActivity(mActivity, DownLoadActivity.class);
                        break;
                    case R.id.ab_search:
                        initPopupWindow(item.getActionView());
                        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(mActivity.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        //ViewAnimationUtils.createCircularReveal()
        //tablayout设置
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorControlNormal));
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);


        //viewpager适配器
        TabsViewPagerAdapter adapter = new TabsViewPagerAdapter(((MainActivity) mActivity).getSupportFragmentManager());
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
        Log.e("weiwei", "AllTabFragment_initData");
    }

    private void initPopupWindow(View v) {
        final View view = LayoutInflater.from(mActivity).inflate(R.layout.item_search_popup, null);
        mEditText = (EditText) view.findViewById(R.id.et_search);
        mIbBack = (ImageButton) view.findViewById(R.id.ib_back);
        mIbSearch = (ImageButton) view.findViewById(R.id.ib_search);
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popWindow.setAnimationStyle(R.style.animatorUP);  //设置加载动画
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.colorPrimaryDarkTranslate));    //要为popWindow设置一个背景才有效
        popWindow.showAtLocation(popWindow.getContentView(), Gravity.TOP, 0, 0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animator animator = startAnimationCir(view);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        popWindow.dismiss();
                    }
                });
            }
        });
        mIbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dismiss();
            }
        });
        mIbSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "搜索功能正在开发", Toast.LENGTH_SHORT).show();
                popWindow.dismiss();
            }
        });


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Animator startAnimationCir(View view) {

        Animator animator = ViewAnimationUtils.createCircularReveal(
                view,
                view.getWidth(),
                0,
                view.getWidth(),
                0);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(400);
        animator.start();
        return animator;
    }

}
