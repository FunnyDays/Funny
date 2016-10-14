package com.carpediem.vv.funny.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.carpediem.vv.funny.Adapter.BookAdapter;
import com.carpediem.vv.funny.Base.BaseFragment;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.bean.BookBean.Book;
import com.carpediem.vv.funny.bean.BookBean.BookTopic;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/6/28.
 */
public class BooksFragment extends BaseFragment {



    private RecyclerView recyclerView;
    private BookAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler  = new Handler();
    private ArrayList<BookTopic> bookTopics= new ArrayList<>();
    private ArrayList<Book> bookList= new ArrayList<>();
    private Toolbar toolbar;


    public static BooksFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        BooksFragment fragment = new BooksFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void initData() {
        BmobQuery<BookTopic> query = new BmobQuery<BookTopic>();
        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        query.findObjects(new FindListener<BookTopic>() {
            @Override
            public void done(List<BookTopic> object, BmobException e) {
                if(e==null){
                    bookTopics.clear();
                    for (BookTopic bookTopic : object) {
                        //获得数据的objectId信息
                        bookTopic.getObjectId();
                        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
                        Log.e("bookTopic","bookTopic查询的数据"+bookTopic.getTopicName()+"   ID   "+bookTopic.getObjectId());
                        bookTopics.add(bookTopic);
                        nextquery();
                    }
                    initRecyclerView();
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

            private void nextquery() {
                if(bookTopics!=null){
                    for (int i = 0; i <bookTopics.size() ; i++) {
                        BmobQuery<Book> query1 = new BmobQuery<Book>();
                        query1.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
                        BookTopic bookTopic = new BookTopic();
                        bookTopic.setObjectId(bookTopics.get(i).getObjectId());
                        query1.addWhereEqualTo("topic",new BmobPointer(bookTopic));
                        query1.findObjects(new FindListener<Book>() {
                            @Override
                            public void done(List<Book> objects,BmobException e) {
                                for (Book book : objects) {
                                    Log.e("bookTopic","bookTopic查询的数据"+book.getBookName()+book.getBookInfo());
                                    bookList.add(book);
                                }

                                swipeRefreshLayout.setRefreshing(false);
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }

        });



    }



    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_books, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_gif);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefreshLayout);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        initToolbar();
        initSwipeRefreshLayout();
        return view;
    }
    private void initToolbar() {
        toolbar.setTitle("每日书屋");
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


    private void initRecyclerView() {
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        //设置adapter
        mAdapter = new BookAdapter(mActivity,bookTopics,bookList);
        recyclerView.setAdapter(mAdapter);
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


}
