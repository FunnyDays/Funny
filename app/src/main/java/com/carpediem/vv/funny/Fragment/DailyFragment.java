package com.carpediem.vv.funny.Fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.carpediem.vv.funny.Activity.CommentActivity;
import com.carpediem.vv.funny.Activity.MainActivity;
import com.carpediem.vv.funny.Base.BaseFragment;
import com.carpediem.vv.funny.Base.DividerItemDecoration;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.bean.FunnyGIF.FunnyGif;
import com.carpediem.vv.funny.weight.BottomTrackListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/6/28.
 */
public class DailyFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private ArrayList<String> mDatas;
    HomeAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler  = new Handler();
    boolean isLoading;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    private static final int TYPE_FOOTER = 0;
    private static final int STATE_REFRESH = 0;// 下拉刷新
    private static final int STATE_MORE = 1;// 加载更多
    private int limit = 10;        // 每页的数据是10条
    private int curPage = 0;        // 当前页的编号，从0开始
    private int isLoadData;
    private String lastTime;
    List<FunnyGif> arrayList = new ArrayList<FunnyGif>();
    private LinearLayoutManager linearLayoutManager;
    private Toolbar toolbar;
    private Typeface typeface;

    public static DailyFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        DailyFragment fragment = new DailyFragment();
        fragment.setArguments(args);
        return fragment;
    }
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
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }, 3000);

    }

    @Override
    protected View initView() {
        typeface = Typeface.createFromAsset(mActivity.getAssets(), "fonts/1.ttf");
        View view = View.inflate(mActivity, R.layout.fragment_daily, null);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        initToolbar();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);
        initSwipeRefreshLayout();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_gif);
        initRecyclerView();

        return view;
    }

    private void initToolbar() {
        toolbar.setTitle("每日GIF");
        toolbar.inflateMenu(R.menu.test);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.action_publish:
                        Toast.makeText(mActivity, "正在开发" + itemId, Toast.LENGTH_SHORT).show();
                    case R.id.menu_refresh:
                        Toast.makeText(mActivity, "正在开发" + itemId, Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });

    }

    private void initSwipeRefreshLayout() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryData(0, STATE_REFRESH);
                  handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (arrayList.size()==0){
                            swipeRefreshLayout.setRefreshing(true);
                        }else {
                            mAdapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 3000);
            }
        });

    }

    private void initRecyclerView() {
        //设置布局管理器
        linearLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置adapter
        recyclerView.setAdapter(mAdapter = new HomeAdapter());
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.HORIZONTAL_LIST));
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

                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadMoreData();
                                Log.d("test", "load more completed");
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });

    }
    class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        @Override
        public int getItemCount()
        {
            return arrayList.size() == 0 ? 10 : arrayList.size() + 1;

        }

        @Override
        public int getItemViewType(int position) {
            if (position + 1 == getItemCount()) {
                return TYPE_FOOTER;
            } else if (position%2==0){
                return TYPE_TWO;
            }else {
                return TYPE_ONE;
            }
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view;
            switch (viewType){
                case 0 :
                    view = LayoutInflater.from(mActivity).inflate(R.layout.item_foot, parent, false);
                    return new FootViewHolder(view);
                case 1:
                    view = LayoutInflater.from(mActivity).inflate(R.layout.item_daily_with_text, parent, false);
                    return new TextViewHolder(view);
                case 2:
                    view = LayoutInflater.from(mActivity).inflate(R.layout.item_with_data, parent, false);
                    return new ItemViewHolder(view);
            }

            return null;

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if(arrayList.size()==0){

            }else  if (holder instanceof ItemViewHolder) {
                ((ItemViewHolder)holder).tv.setText(arrayList.get(position).getTextContent());
                ((ItemViewHolder)holder).tvTime.setText(arrayList.get(position).getCreatedAt());

                if (arrayList.get(position).getGifContent() != null) {
                    Glide.with(mActivity)
                            .load(arrayList.get(position).getGifContent().getUrl())
                            .fitCenter()
                            .placeholder(R.mipmap.ic_launcher)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(((ItemViewHolder)holder).imageView);
                }
                ((ItemViewHolder)holder).buttonLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mActivity, "正在开发中", Toast.LENGTH_SHORT).show();
                    }
                }); ((ItemViewHolder)holder).buttonDislike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mActivity, "正在开发中", Toast.LENGTH_SHORT).show();
                    }
                }); ((ItemViewHolder)holder).buttonShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mActivity, "正在开发中", Toast.LENGTH_SHORT).show();
                    }
                }); ((ItemViewHolder)holder).buttonComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(mActivity, "正在开发中", Toast.LENGTH_SHORT).show();
                        Intent mIntent=new Intent(mActivity,CommentActivity.class);
                        mIntent.putExtra("ObjectId",arrayList.get(position).getObjectId());
                        mActivity.startActivity(mIntent);
                       // IntentUtils.startActivity(mActivity, CommentActivity.class);
                    }
                });
            }else if (holder instanceof TextViewHolder){

                ((TextViewHolder)holder).tv.setTypeface(typeface);
                ((TextViewHolder)holder).tv.setText(arrayList.get(position).getTextJoke());

            }
        }

         class ItemViewHolder extends RecyclerView.ViewHolder {

             TextView tv;
             TextView tvTime;
             ImageView imageView;
             Button buttonLike ;
             Button buttonDislike;
             Button buttonShare ;
             Button buttonComment;
            public ItemViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.text_content);
                tvTime = (TextView) view.findViewById(R.id.base_swipe_group_item_time);
                imageView = (ImageView) view.findViewById(R.id.image_gif);
                 buttonLike = (Button) view.findViewById(R.id.button_like);
                 buttonDislike = (Button) view.findViewById(R.id.button_dislike);
                 buttonShare = (Button) view.findViewById(R.id.button_share);
                 buttonComment = (Button) view.findViewById(R.id.button_comment);
            }
        }
        class TextViewHolder extends RecyclerView.ViewHolder {
            TextView tv;
            Button buttonLike ;
            Button buttonDislike;
            Button buttonShare ;
            Button buttonComment;
            public TextViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.text_joke);
                buttonLike = (Button) view.findViewById(R.id.button_like);
                buttonDislike = (Button) view.findViewById(R.id.button_dislike);
                buttonShare = (Button) view.findViewById(R.id.button_share);
                buttonComment = (Button) view.findViewById(R.id.button_comment);
            }
        }
        class FootViewHolder extends RecyclerView.ViewHolder {

            public FootViewHolder(View view) {
                super(view);
            }
        }

    }
    private void loadMoreData() {

        queryData(curPage, STATE_MORE);
        Log.e("bmob查询的数据", curPage + "更多的护具查询：curPage");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                mAdapter.notifyItemRemoved(mAdapter.getItemCount());


            }
        }, 3000);

    }
    /**
     * 分页获取数据
     *
     * @param page       页码
     * @param actionType recyclerView的操作类型（下拉刷新、上拉加载更多）
     */
    private void queryData(int page, final int actionType) {
        Log.e("bmob", "pageN:" + page + " limit:" + limit + " actionType:" + actionType);
        BmobQuery<FunnyGif> query = new BmobQuery<>();
        // 先从缓存获取数据，如果没有，再从网络获取。
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
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
        query.findObjects(new FindListener<FunnyGif>() {
            @Override
            public void done(List<FunnyGif> list, BmobException e) {
                if (e == null) {
                    // Toast.makeText(mActivity, "查询成功共"+list.size()+"条数据", Toast.LENGTH_SHORT).show();
                    Log.e("bmob查询的数据", "查询成功共" + list.size() + "条数据");
                    if (actionType == STATE_MORE) {
                        isLoadData = 0;
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
                    for (FunnyGif fg : list) {
                        arrayList.add(fg);

                        Log.e("arrayList","arrayList：成功"+fg.getTextContent());
                    }
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
