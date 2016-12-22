package com.carpediem.vv.funny.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.carpediem.vv.funny.Activity.DownLoadActivity;
import com.carpediem.vv.funny.Activity.MainActivity;
import com.carpediem.vv.funny.Adapter.MainContentVPAdapter;
import com.carpediem.vv.funny.Adapter.SearchAdapter;
import com.carpediem.vv.funny.Adapter.TabsViewPagerAdapter;
import com.carpediem.vv.funny.Base.BaseFragment;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.Utils.IntentUtils;
import com.carpediem.vv.funny.bean.SearchBean;

import java.util.ArrayList;

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
    private RecyclerView mLvHistory;
    private ArrayList<BaseFragment> fragments;
    private TabsViewPagerAdapter mAdapter;

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
        //tablayout设置
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorControlNormal));
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        //viewpager适配器
        getFragments();
        mAdapter.addFragment(fragments.get(0), "视频");
        mAdapter.addFragment(fragments.get(1), "视频");
        mAdapter.addFragment(fragments.get(2), "游戏");
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
    private ArrayList<BaseFragment> getFragments() {
        fragments = new ArrayList<>();
        fragments.add(MovieFragment.newInstance("视频"));
        fragments.add(MovieFragment.newInstance("视频"));
        fragments.add(GameFragment.newInstance("游戏"));
        mAdapter = new TabsViewPagerAdapter(((MainActivity) mActivity).getSupportFragmentManager());
        return fragments;
    }
    @Override
    public void initData() {
        int tabPosition = tabLayout.getSelectedTabPosition();
       /* fragments.get(0).initData();
        fragments.get(1).initData();
        fragments.get(2).initData();*/
        Log.e("weiwei12", "AllTabFragment_initData"+tabPosition);
    }

    /**
     * popupWindow弹窗
     * @param v
     */
    private void initPopupWindow(View v) {
        final View searchView = LayoutInflater.from(mActivity).inflate(R.layout.item_search_popup, null);

        mEditText = (EditText) searchView.findViewById(R.id.et_search);
        mIbBack = (ImageButton) searchView.findViewById(R.id.ib_back);
        mIbSearch = (ImageButton) searchView.findViewById(R.id.ib_search);
        mLvHistory = (RecyclerView) searchView.findViewById(R.id.lv_history);
        initRecyclerView();
        final PopupWindow popWindow = new PopupWindow(searchView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popWindow.setAnimationStyle(R.style.animatorUP);  //设置加载动画
        popWindow.setBackgroundDrawable(getResources().getDrawable(R.color.colorPrimaryDarkTranslate));    //要为popWindow设置一个背景才有效
        popWindow.showAtLocation(popWindow.getContentView(), Gravity.TOP, 0, 0);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(mActivity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                Animator animator = startAnimationCir(view);
                if (animator!=null){
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            popWindow.dismiss();
                        }
                    });
                }else {
                    popWindow.dismiss();
                }

            }
        });
        mEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ( keyCode == KeyEvent.KEYCODE_BACK){
                    Animator animator = startAnimationCir(searchView);
                    if (animator!=null){
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                popWindow.dismiss();
                            }
                        });
                    }else {
                        popWindow.dismiss();
                    }
                }
                return false;
            }
        });
        mIbBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(mActivity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                Animator animator = startAnimationCir(searchView);
                if (animator!=null){
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            popWindow.dismiss();
                        }
                    });
                }else {
                    popWindow.dismiss();
                }
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

    private void initRecyclerView() {
        ArrayList<SearchBean> beanArrayList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            SearchBean searchBean = new SearchBean("推荐搜索" + i, "");
            beanArrayList.add(searchBean);
        }
        SearchAdapter searchAdapter = new SearchAdapter(mActivity, beanArrayList);
        mLvHistory.setLayoutManager(new LinearLayoutManager(mActivity));
        mLvHistory.setAdapter(searchAdapter);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private Animator startAnimationCir(View view) {
        Animator animator = ViewAnimationUtils.createCircularReveal(
                view,
                view.getWidth()- mIbSearch.getWidth()/2-(int)getResources().getDisplayMetrics().density*10,
                mIbSearch.getHeight()/2+(int)getResources().getDisplayMetrics().density*10,
                view.getHeight(),
                0);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(300);
        animator.start();
        return animator;
    }
    public static boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            Log.e("weiwei", "AllTabFragment事件OK");
        }
        return true;
    }

}
