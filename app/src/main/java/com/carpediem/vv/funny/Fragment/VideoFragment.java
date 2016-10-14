package com.carpediem.vv.funny.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.carpediem.vv.funny.Activity.PlayVideoActivity;
import com.carpediem.vv.funny.Adapter.StaggeredHomeAdapter;
import com.carpediem.vv.funny.Base.BaseFragment;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.bean.VideoBean.VideoBean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/6/28.
 */
public class VideoFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private List<VideoBean> mDatas;
    private StaggeredHomeAdapter mStaggeredHomeAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler=new Handler();


    public static VideoFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void initData() {
        Log.e("bmob", "：initData开始执行" );
        mDatas = new ArrayList<VideoBean>();
        BmobQuery<VideoBean> query = new BmobQuery<VideoBean>();
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.findObjects(new FindListener<VideoBean>() {
            @Override
            public void done(List<VideoBean> object, BmobException e) {
                if (e == null) {
                    mDatas.clear();
                    for (VideoBean videoBean : object) {
                        mDatas.add(videoBean);

                        if (mStaggeredHomeAdapter!=null){
                            mStaggeredHomeAdapter.notifyDataSetChanged();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }

                } else {
                    Log.e("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }


    @Override
    protected View initView() {
        Log.e("bmob", "：initView开始执行" );
        View view = View.inflate(mActivity, R.layout.fragment_video, null);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);
        initSwipeRefreshLayout();
        mRecyclerView = (RecyclerView)view.findViewById(R.id.id_recyclerview);
        Log.e("bmob", "：" +mDatas.size());
        mStaggeredHomeAdapter = new StaggeredHomeAdapter(mActivity, mDatas);

        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mStaggeredHomeAdapter);
        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        initEvent();
        return view;
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

    }
    private void initEvent()
    {
        mStaggeredHomeAdapter.setOnItemClickLitener(new StaggeredHomeAdapter.OnItemClickLitener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                Intent mIntent=new Intent(mActivity,PlayVideoActivity.class);
                mIntent.putExtra("VideoFileURL",mDatas.get(position).getVideoFile().getFileUrl());
                mActivity.startActivity(mIntent);

            }

            @Override
            public void onItemLongClick(View view, int position)
            {
                Toast.makeText(mActivity,
                        position + " long click", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
