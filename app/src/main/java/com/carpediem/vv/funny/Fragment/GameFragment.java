package com.carpediem.vv.funny.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.carpediem.vv.funny.Activity.GameDetailActivity;
import com.carpediem.vv.funny.Activity.MainActivity;
import com.carpediem.vv.funny.Adapter.GameAdapter;
import com.carpediem.vv.funny.Adapter.GameDetailPicAdapter;
import com.carpediem.vv.funny.Base.BaseFragment;

import com.carpediem.vv.funny.DataParserBean.DataParser;
import com.carpediem.vv.funny.R;

import com.carpediem.vv.funny.Utils.IntentUtils;
import com.carpediem.vv.funny.Utils.T;
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

    private Handler handler = new Handler();
    boolean isLoading = false;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    private static final int STATE_REFRESH = 0;// 下拉刷新
    private static final int STATE_MORE = 1;// 加载更多
    private int action = STATE_REFRESH;        // 每页的数据是10条
    private int curPage = 1;        // 当前页的编号，从1开始
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
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    mAdapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                }
            }
        };
        new Thread() {
            @Override
            public void run() {
                ParserHtml(curPage, action);
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }.start();
    }

    private void ParserHtml(int page, int action) {
        if (action == STATE_REFRESH) {
            ArrayList<Game> gameArrayList1 = DataParser.getAllGame(page);
            arrayList.addAll(gameArrayList1);

        }
        if (action == STATE_MORE) {
            ArrayList<Game> gameArrayList1 = DataParser.getAllGame(page);
            arrayList.addAll(gameArrayList1);
        }
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
                arrayList.clear();
                curPage=1;
                action=STATE_REFRESH;
                initData();
            }
        });
    }

    private void initRecyclerView() {
        linearLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new GameAdapter(mActivity, arrayList);
        mAdapter.setOnItemClickLitener(new GameDetailPicAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mActivity, GameDetailActivity.class);
                intent.putExtra("gameLink",arrayList.get(position).getGameDetailLink());
                intent.putExtra("gameStar",arrayList.get(position).getGameStar());
                Log.e("weiwei",arrayList.get(position).getGameDetailLink());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
        //监听手指滑动
        recyclerView.addOnScrollListener(new BottomTrackListener(MainActivity.bottomNavigationBar));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                switch (newState) {
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        Log.i("Main", "用户在手指离开屏幕之前，由于滑了一下，视图仍然依靠惯性继续滑动");
                        //Glide.with(mActivity).pauseRequests();
                        //刷新
                        break;
                    case RecyclerView.SCROLL_STATE_IDLE:
                        Log.i("Main", "视图已经停止滑动");
                        // Glide.with(mActivity).resumeRequests();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        Log.i("Main", "手指没有离开屏幕，视图正在滑动");
                        // Glide.with(mActivity).resumeRequests();
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                if (!recyclerView.canScrollVertically(-1)) {
                    //T.show(mActivity,"已经到第一条");
                } else if (!recyclerView.canScrollVertically(1)) {
                    //T.show(mActivity,"到了最后一条");
                } else if (dy < 0) {
                    //T.show(mActivity,"正在向上滑动");
                } else if (dy > 0) {
                    // T.show(mActivity,"正在向下滑动");
                    if (lastVisibleItemPosition + 1 == mAdapter.getItemCount() - 5) {
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
            }
        });
    }

    /**
     * 加载更多
     */
    private void loadMoreData() {
        curPage++;
        action=STATE_MORE;
        initData();
    }

}
