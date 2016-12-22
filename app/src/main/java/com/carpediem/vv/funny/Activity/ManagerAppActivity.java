package com.carpediem.vv.funny.Activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.carpediem.vv.funny.Adapter.ManagerAppAdapter;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.Utils.AppUtils;
import com.carpediem.vv.funny.Utils.Loading.LoadingLayout;
import com.carpediem.vv.funny.Utils.NetUtils;

import java.util.ArrayList;
import java.util.List;

public class ManagerAppActivity extends AppCompatActivity {

    private RecyclerView mRvAppList;
    private Toolbar mToolbar;
    private List<AppUtils.AppInfo> appsInfo=new ArrayList<>();
    Handler hanlder=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                mLoadingLayout.setStatus(LoadingLayout.Success);//成功
                mAdapter.notifyDataSetChanged();
            }
        }
    };
    private LoadingLayout mLoadingLayout;
    private ManagerAppAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_app);
        mLoadingLayout = (LoadingLayout) findViewById(R.id.activity_manager_app);
        initData();
        initEmptyView();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar();
        mRvAppList = (RecyclerView) findViewById(R.id.recyclerView);
        initRecycle();
    }
    /**
     * 空布局
     */
    private void initEmptyView() {
        mLoadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                //Toast.makeText(mActivity, "重试", Toast.LENGTH_SHORT).show();
                initEmptyView();
                initData();
            }
        });
        mLoadingLayout.setStatus(LoadingLayout.Loading);//加载中
    }

    /**
     * 获取所有App安装信息
     */
    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AppUtils.AppInfo> appInfos = AppUtils.getAppsInfoNoSys(ManagerAppActivity.this);
                appsInfo.addAll(appInfos);
                for (int i = 0; i < appsInfo.size(); i++) {
                    Log.e("appinfo", "所有app的信息："+appsInfo.toString());
                }
                hanlder.obtainMessage(1).sendToTarget();
            }
        }).start();


    }

    private void initRecycle() {
        mRvAppList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ManagerAppAdapter(this, appsInfo);
        mRvAppList.setAdapter(mAdapter);

    }

    private void initToolbar() {
        mToolbar.setTitle("软件管理");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
