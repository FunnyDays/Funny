package com.carpediem.vv.funny.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.carpediem.vv.funny.Activity.MainActivity;
import com.carpediem.vv.funny.Adapter.GameAdapter;
import com.carpediem.vv.funny.Base.BaseFragment;
import com.carpediem.vv.funny.R;

import com.carpediem.vv.funny.bean.FunnyGIF.FunnyGif;
import com.carpediem.vv.funny.bean.GameBean.Game;
import com.carpediem.vv.funny.weight.BottomTrackListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/6/28.
 */
public class GameFragment extends BaseFragment {

    private Handler handler  = new Handler();
    boolean isLoading=false;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private static final int STATE_REFRESH = 0;// 下拉刷新
    private static final int STATE_MORE = 1;// 加载更多
    private int limit = 10;        // 每页的数据是10条
    private int curPage = 0;        // 当前页的编号，从0开始
    private int isLoadData;
    private String lastTime;
    ArrayList<Game> arrayList = new ArrayList<Game>();
    private LinearLayoutManager linearLayoutManager;
    private GameAdapter mAdapter;
    private boolean queryDone;

    public static GameFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        queryData(0, STATE_REFRESH);
        this.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (arrayList.size()==0){
                    //swipeRefreshLayout.setRefreshing(true);
                }else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        }, 3000);
    }



    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_game, null);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);
        initRefreshlayout();
        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
        initRecyclerView();
        return view;
    }

    private void initRefreshlayout() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryData(0, STATE_REFRESH);
                if (queryDone){
                    mAdapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                }
            }
        });
    }
    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new GameAdapter(mActivity, arrayList);
        recyclerView.setAdapter(mAdapter);
        //监听手指滑动
        recyclerView.addOnScrollListener(new BottomTrackListener(MainActivity.bottomNavigationBar));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                switch (newState){
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        Log.i("Main","用户在手指离开屏幕之前，由于滑了一下，视图仍然依靠惯性继续滑动");
                        Glide.with(mActivity).pauseRequests();
                        //刷新
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:
                        Log.i("Main", "视图已经停止滑动");
                        Glide.with(mActivity).resumeRequests();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        Log.i("Main","手指没有离开屏幕，视图正在滑动");
                        Glide.with(mActivity).resumeRequests();
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {
                    Log.e("test", "loading executed");

                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadMoreData();
                                Log.d("test", "load more completed");
                                isLoading = false;
                            }
                        }, 500);
                    }
                }
            }
        });
    }

    /**
     * 加载更多
     */
    private void loadMoreData() {
        queryData(curPage, STATE_MORE);

    }

    /**
     * 分页获取数据
     *
     * @param page       页码
     * @param actionType recyclerView的操作类型（下拉刷新、上拉加载更多）
     */
    private void queryData(int page, final int actionType) {
        Log.e("bmob", "pageN:" + page + "limit:" + limit + " actionType:" + actionType);
        final BmobQuery<Game> query = new BmobQuery<>();
        // 先从缓存获取数据，如果没有，再从网络获取。
        //query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        // 按时间降序查询
        query.order("-createdAt");
        // 如果是加载更多
        if (actionType == STATE_MORE) {
            Log.e("bmob查询的数据", "curPage:" + curPage + " limit:" + limit + " actionType:" + actionType);
            // 处理时间查询
            Date date = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {

                date = sdf.parse(lastTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // 只查询小于等于最后一个item发表时间的数据
            //query.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date));
            // 跳过之前页数并去掉重复数据

            query.setSkip(curPage * limit);

            // query.setSkip(curPage * limit+1);

        } else {
            curPage = 0;
            query.setSkip(curPage);

        }
        // 设置每页数据个数
        query.setLimit(limit);
        // 查找数据
        query.findObjects(new FindListener<Game>() {
            @Override
            public void done(List<Game> list, BmobException e) {
                if (e == null) {
                    // Toast.makeText(mActivity, "查询成功共"+list.size()+"条数据", Toast.LENGTH_SHORT).show();
                    Log.e("bmob查询的数据", "查询成功共" + list.size() + "条数据");
                    if (actionType == STATE_MORE) {
                        if (list.size() == 0) {
                            Toast.makeText(mActivity, "没有更多数据了", Toast.LENGTH_SHORT).show();
                        }

                    }
                    if (actionType == STATE_REFRESH) {
                        // 当是下拉刷新操作时，将当前页的编号重置为0，并把bankCards清空，重新添加
                        curPage = 0;
                        arrayList.clear();
                        // 获取最后时间
                        lastTime = list.get(list.size() - 1).getCreatedAt();
                    }
                    // 将本次查询的数据添加到arrayList中
                    for (Game fg : list) {
                        arrayList.add(fg);
                        Log.e("bmob查询的数据", " fg.getGameName();：" +  fg.getGameName());
                    }
                    mAdapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                    // 这里在每次加载完数据后，将当前页码+1，这样在上拉刷新的onPullUpToRefresh方法中就不需要操作curPage了
                    curPage++;
                    Log.e("bmob查询的数据++", "curPage：" + curPage);

                } else if (actionType == STATE_MORE) {
                    Toast.makeText(mActivity, "没有更多数据了", Toast.LENGTH_SHORT).show();
                } else if (actionType == STATE_REFRESH) {
                    Toast.makeText(mActivity, "没有数据", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
