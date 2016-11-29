package com.carpediem.vv.funny.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.carpediem.vv.funny.Adapter.GameDetailPicAdapter;
import com.carpediem.vv.funny.DataParserBean.DataParser;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.Utils.LG;
import com.carpediem.vv.funny.Utils.T;
import com.carpediem.vv.funny.bean.GameBean.GameDetail;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/21.
 */

public class GameDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList arrayList = null;
    private Toolbar toolbar;
    private ImageView gamePic, gamePic1, gamePic2, gamePic3,gamePic4;
    private TextView gameName;
    private TextView gameEnName;
    private RatingBar gameRatingBar;
    private Float gameStar;
    private GameDetailPicAdapter gameDetailPicAdapter;
    private GameDetail gameDetail;
    private ArrayList<String> gamePics = new ArrayList<>();
    private TextView textView, textView1, textView2, textView3, textView8, textView9, textView10,
            textView4, textView5, textView6, textView7;
    private String gameNameTitle;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        initData();
        initToolBar();
        initView();

    }

    private void initView() {
        gamePic = (ImageView) findViewById(R.id.iv_game_pic);
        gameName = (TextView) findViewById(R.id.tv_game_name);
        gameEnName = (TextView) findViewById(R.id.tv_game_name_en);
        gameRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        gameRatingBar.setRating(gameStar);

        textView = (TextView) findViewById(R.id.tv_game_type);
        textView1 = (TextView) findViewById(R.id.tv_game_edition);
        textView6 = (TextView) findViewById(R.id.tv_game_time);
        textView2 = (TextView) findViewById(R.id.tv_game_size);
        textView3 = (TextView) findViewById(R.id.tv_game_lang);
        textView4 = (TextView) findViewById(R.id.tv_game_maker);
        textView5 = (TextView) findViewById(R.id.tv_game_system);
        textView7 = (TextView) findViewById(R.id.tv_content);

        gamePic1 = (ImageView) findViewById(R.id.iv_game_one);
        gamePic2 = (ImageView) findViewById(R.id.iv_game_two);
        gamePic3 = (ImageView) findViewById(R.id.iv_game_three);
        gamePic4 = (ImageView) findViewById(R.id.iv_game_intro_more);

        textView8 = (TextView) findViewById(R.id.tv_game_one);
        textView9 = (TextView) findViewById(R.id.tv_game_two);
        textView10 = (TextView) findViewById(R.id.tv_game_three);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        gameDetailPicAdapter = new GameDetailPicAdapter(this, gamePics);
        gameDetailPicAdapter.setOnItemClickLitener(new GameDetailPicAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(GameDetailActivity.this,imageDetailPagerActivity.class)
                        .putStringArrayListExtra("gameLink",gamePics));
            }
        });
        recyclerView.setAdapter(gameDetailPicAdapter);


    }

    private void initData() {
        final String gameLink = getIntent().getStringExtra("gameLink");
        gameNameTitle = getIntent().getStringExtra("gameName");
        gameStar = getIntent().getFloatExtra("gameStar", 5);
        final Handler handler = new Handler() {
            private Boolean mState=false;
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1 && gameDetail != null) {
                    gamePics.addAll(gameDetail.getGamePic());
                    gameDetailPicAdapter.notifyDataSetChanged();
                    Glide.with(GameDetailActivity.this).load(gameDetail.getGameIcon()).into(gamePic);
                    Glide.with(GameDetailActivity.this).load(gameDetail.getGameRecommendImage().get(0)).into(gamePic1);
                    Glide.with(GameDetailActivity.this).load(gameDetail.getGameRecommendImage().get(1)).into(gamePic2);
                    Glide.with(GameDetailActivity.this).load(gameDetail.getGameRecommendImage().get(2)).into(gamePic3);
                    gameName.setText(gameNameTitle);
                    gameEnName.setText(gameDetail.getGameEnName());
                    textView.setText("•" + gameDetail.getGameAllIntro().get(0));
                    textView1.setText("•" + gameDetail.getGameAllIntro().get(1));
                    textView2.setText("•" + gameDetail.getGameAllIntro().get(2));
                    textView3.setText("•" + gameDetail.getGameAllIntro().get(3));
                    textView4.setText("•" + gameDetail.getGameAllIntro().get(7));
                    textView5.setText("•" + gameDetail.getGameAllIntro().get(8));
                    textView6.setText("•" + gameDetail.getGameAllIntro().get(6));

                    textView8.setText(gameDetail.getGamerecommendName().get(0));
                    textView9.setText(gameDetail.getGamerecommendName().get(1));
                    textView10.setText(gameDetail.getGamerecommendName().get(2));

                    //监听游戏介绍显示方式
                    textView7.setText(gameDetail.getGameDetailIntro());
                    gamePic4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mState){
                                mState = false;
                                textView7.setEllipsize(TextUtils.TruncateAt.END);
                                textView7.setMaxLines(3);
                                gamePic4.setImageResource(R.drawable.ic_open_more_text);
                            }else {
                                mState = true;
                                textView7.setEllipsize(null);
                                textView7.setMaxLines(Integer.MAX_VALUE);
                                gamePic4.setImageResource(R.drawable.ic_close_more_text);

                            }
                        }
                    });

                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                gameDetail = DataParser.getGameDetail(gameLink);
                if (gameDetail != null) {
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }

            }
        }.start();
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(gameNameTitle);
        toolbar.inflateMenu(R.menu.game_detail_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_share:
                        new ShareAction(GameDetailActivity.this).withText("hello")
                                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,
                                        SHARE_MEDIA.WEIXIN,SHARE_MEDIA.EMAIL,SHARE_MEDIA.SMS)
                                .setCallback(umShareListener).open();
                        break;
                }
                return false;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);

            Toast.makeText(GameDetailActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(GameDetailActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(GameDetailActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
