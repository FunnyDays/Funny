package com.carpediem.vv.funny.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.carpediem.vv.funny.Adapter.DownloadAdapter;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.bean.download.FileInfo;
import com.carpediem.vv.funny.bean.download.ThreadInfo;
import com.carpediem.vv.funny.db.ThreadDaoImpl;
import com.carpediem.vv.funny.services.DownLoadServices;


import java.util.ArrayList;
import java.util.List;

import static com.carpediem.vv.funny.Utils.Utils.context;

public class DownLoadActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private DownloadAdapter mDownloadAdapter;
    private TextView mTextView;
    private ThreadDaoImpl mThreadDao;
    private List<FileInfo> mFileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownLoadServices.ACTION_UPDATE);
        filter.addAction(DownLoadServices.ACTION_FINISH);
        registerReceiver(broadcast, filter);
        initView();
        initData();

    }

    private void initData() {
        mThreadDao = new ThreadDaoImpl(this);
        List<ThreadInfo> infoList = mThreadDao.getAllThreads();
        for (int i = 0; i < infoList.size(); i++) {
            Log.e("weiwei",infoList.get(i).toString());
            int progess = (int)(infoList.get(i).getFinished() * 100 / infoList.get(i).getLength());
            mFileList.add(new FileInfo(0,infoList.get(i).getUrl(),infoList.get(i).getIcon(),infoList.get(i).getName(),progess,0));
        }
        mDownloadAdapter.notifyDataSetChanged();
        if (infoList.size() ==0) {
            mTextView.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("下载管理");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.lv_download);
        initRecyclerView();
        mTextView = (TextView) findViewById(R.id.empty_list_view);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
       mDownloadAdapter = new DownloadAdapter(this, mFileList);
        mRecyclerView.setAdapter(mDownloadAdapter);
    }
    /**
     * 更新UI的广播接收器
     */
    BroadcastReceiver broadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownLoadServices.ACTION_UPDATE.equals(intent.getAction())) {
                int finished = intent.getIntExtra("finished", 0);
                String url = intent.getStringExtra("url");
                mDownloadAdapter.updateProgress(url,finished);
                Log.e("download", "init大小:" + finished);
            } else if (DownLoadServices.ACTION_FINISH.equals(intent.getAction())) {
                FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
                Toast.makeText(DownLoadActivity.this, fileInfo.getFileName() + "下载完成", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
       unregisterReceiver(broadcast);
    }
}
