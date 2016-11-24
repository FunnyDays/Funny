package com.carpediem.vv.funny.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.carpediem.vv.funny.Adapter.GameDetailPicAdapter;
import com.carpediem.vv.funny.DataParserBean.DataParser;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.Utils.LG;
import com.carpediem.vv.funny.bean.GameBean.GameDetail;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/21.
 */

public class GameDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList arrayList;
    private Toolbar toolbar;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        initData();
        initToolBar();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new GameDetailPicAdapter(this));
    }

    private void initData() {
        final String gameLink = getIntent().getStringExtra("gameLink");
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    //mAdapter.notifyDataSetChanged();
                    //refreshLayout.setRefreshing(false);
                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                GameDetail gameDetail = DataParser.getGameDetail(gameLink);
                ArrayList<String> gamePic = gameDetail.getGamePic();
                for (int i = 0; i < gamePic.size(); i++) {
                    String s = gamePic.get(i);
                    //LG.d("weiwei",s+"hehe");
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }.start();
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
