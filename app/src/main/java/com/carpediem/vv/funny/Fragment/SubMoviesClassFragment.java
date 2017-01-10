package com.carpediem.vv.funny.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carpediem.vv.funny.Activity.MovieDetailActivity;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.Utils.Loading.Utils;
import com.carpediem.vv.funny.Utils.net.HtmlAnalyzeUtil;
import com.carpediem.vv.funny.bean.MovieBean.MovieInfo;
import com.carpediem.vv.funny.weight.RecyclerViewDivider;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by LG on 2016/11/26.
 */

public class SubMoviesClassFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout refreshLayout;
    private RVAdapter mAdapter;
    private String TAG = "lgst";
    List<MovieInfo> movies = new ArrayList<MovieInfo>();
    private LinearLayoutManager layoutManager;
    private int page = 1;
    private int id;
    private boolean loding;
    private boolean isClear;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sub_movie_class, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        refreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.refresh);
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                isClear = true;
                initData();
            }
        });
//        refreshLayout.setProgressViewOffset(true, 64, 120);
        mAdapter = new RVAdapter();
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(),
                RecyclerViewDivider.HORIZONTAL, Utils.dp2px(getContext(), 8), Color.parseColor("#EEEEEE")));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int last = layoutManager.findLastVisibleItemPosition();//当前显示的最后一条数据
                int size = mAdapter.getItemCount() - 2;//倒数第二个数据
                if (last == size && !loding) {
                    page++;
                    loding = true;
                    initData();
                }
            }
        });
        id = getArguments().getInt("id");
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
        initData();
    }

    private void initData() {
        HtmlAnalyzeUtil.analyzeMovieList(id, page, new HtmlAnalyzeUtil.HtmlCallBack() {
            @Override
            public void callBack(List<MovieInfo> movies) {
                if (isClear) {//下拉刷新
                    SubMoviesClassFragment.this.movies.clear();
                    isClear=false;
                }
                SubMoviesClassFragment.this.movies.addAll(movies);
                mAdapter.notifyDataSetChanged();
                loding = false;
                refreshLayout.setRefreshing(false);
            }
        });
    }

    class RVAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            MyViewHolder mvh = (MyViewHolder) holder;
            MovieInfo movie = movies.get(position);
            mvh.mTitle.setText(movie.getTitle());
            mvh.mActor.setText(movie.getActor());
            mvh.mDate.setText(movie.getDate());
            Glide.with(getActivity())
                    .load(movie.getImage())
                    .into(mvh.mImg);
            mvh.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), MovieDetailActivity.class)
                            .putExtra("movie", movies.get(position)));
                }
            });
            mvh.mType.setText(movie.getType());
            mvh.mArea.setText(movie.getArea());
        }

        @Override
        public int getItemCount() {
            return movies.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            View rootView;
            ImageView mImg;
            TextView mTitle;
            TextView mType;
            TextView mActor;
            TextView mArea;
            TextView mDate;

            MyViewHolder(View rootView) {
                super(rootView);
                this.rootView = rootView;
                this.mImg = (ImageView) rootView.findViewById(R.id.img);
                this.mTitle = (TextView) rootView.findViewById(R.id.title);
                this.mType = (TextView) rootView.findViewById(R.id.type);
                this.mActor = (TextView) rootView.findViewById(R.id.actor);
                this.mArea = (TextView) rootView.findViewById(R.id.area);
                this.mDate = (TextView) rootView.findViewById(R.id.date);
            }
        }
    }
}
