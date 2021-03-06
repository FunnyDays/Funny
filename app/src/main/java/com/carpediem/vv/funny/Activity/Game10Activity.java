package com.carpediem.vv.funny.Activity;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.carpediem.vv.funny.Adapter.GameDetailPicAdapter;
import com.carpediem.vv.funny.DataParserBean.DataParser;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.Utils.CacheUtils;
import com.carpediem.vv.funny.Utils.FileUtils;
import com.carpediem.vv.funny.Utils.Loading.LoadingLayout;
import com.carpediem.vv.funny.Utils.NetUtils;
import com.carpediem.vv.funny.bean.GameBean.GameDetail;
import com.carpediem.vv.funny.bean.download.FileInfo;
import com.carpediem.vv.funny.bean.download.ThreadInfo;
import com.carpediem.vv.funny.db.ThreadDaoImpl;
import com.carpediem.vv.funny.download.DownloadInfo;
import com.carpediem.vv.funny.download.DownloadManager;
import com.carpediem.vv.funny.download.DownloadState;
import com.carpediem.vv.funny.download.DownloadViewHolder;
import com.carpediem.vv.funny.services.DownLoadServices;
import com.carpediem.vv.funny.services.DownloadTask;
import com.carpediem.vv.funny.weight.ProgressButton;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/11/21.
 */

public class Game10Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList arrayList = null;
    private Toolbar toolbar;
    private ImageView gamePic, gamePic1, gamePic2, gamePic3, gamePic4;
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
    private LoadingLayout mLoadingLayout;
    private ProgressButton mBtInstallGame;
    private String mGameLink;
    private Handler mHandler;
    private DownloadInfo mDownloadInfo;
    private DbManager mDb;
    private DownloadManager downloadManager;
    private DownloadItemViewHolder mViewHolder;

    private String mFirstGameUrl="";
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        installOpenGame();
        checkDownload();
        initData();
        getDownloadInfo();
        initDownload();
        initButtonDownload();
        initView();
        initToolBar();
    }

    private void installOpenGame() {
        List<File> fileList = FileUtils.listFilesInDirWithFilter(DownLoadServices.DOWNLOAD_PATH, "apk", false);
        if (fileList != null) {
            for (int i = 0; i < fileList.size(); i++) {
                Log.e("app", fileList.get(i).getName() + "===" + fileList.get(i).length());
            }
        }
    }

    /**
     * 检查app是否已经安装或者正在下载
     */
    private void checkDownload() {

    }

    private void initView() {
        mLoadingLayout = (LoadingLayout) findViewById(R.id.empty_view_game);
        initEmptyView();

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
                startActivity(new Intent(Game10Activity.this, imageDetailPagerActivity.class)
                        .putStringArrayListExtra("gameLink", gamePics));
            }
        });
        recyclerView.setAdapter(gameDetailPicAdapter);


    }
    /**
     * 获取下载信息状态
     */
    private void getDownloadInfo() {
        downloadManager = DownloadManager.getInstance();
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("download")
                .setDbVersion(1);
        mDb = x.getDb(daoConfig);
        try {
            mDownloadInfo = mDb.selector(DownloadInfo.class)
                    .where("label", "=", gameNameTitle)
                    .and("fileSavePath", "=", FileUtils.fileSavePath+gameNameTitle+".apk")
                    .findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (mDownloadInfo != null) {
            // mDownloadInfo.setState(DownloadState.STOPPED);
            //mDownloadInfo=downloadManager.getDownloadInfo(0);
            Log.e("download", "详情里面的"+mDownloadInfo.toString()+"---------");
        }

    }
    private void initDownload() {
        gameEnName = (TextView) findViewById(R.id.tv_game_name_en);
        mBtInstallGame = (ProgressButton) findViewById(R.id.bt_install_app);
        if (mDownloadInfo!=null){
            mViewHolder = new DownloadItemViewHolder(null,mDownloadInfo);
            mViewHolder.refresh();
            if (mDownloadInfo.getState().value() < DownloadState.FINISHED.value()){
                try {
                    downloadManager.startDownload(
                            mDownloadInfo.getUrl(), mDownloadInfo.getGamePicUrl(), mDownloadInfo.getLabel(),
                            mDownloadInfo.getFileSavePath(), mDownloadInfo.isAutoResume(), mDownloadInfo.isAutoRename(), mViewHolder);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
            mBtInstallGame.setTag(1);
        }else{
            mBtInstallGame.setTag(0);
        }/*else {
            DownloadInfo downloadInfo = new DownloadInfo();
            downloadInfo.setUrl("");
            downloadInfo.setAutoResume(true);
            downloadInfo.setAutoRename(false);
            downloadInfo.setLabel(gameNameTitle);
            downloadInfo.setFileSavePath(FileUtils.fileSavePath+gameNameTitle+".apk");
            mViewHolder = new DownloadItemViewHolder(null,downloadInfo);
        }*/
    }

    private void initButtonDownload() {
        mBtInstallGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 防止开启多个异步线程
                if ((Integer) mBtInstallGame.getTag() == 0) {
                    //获取游戏下载链接
                    getGameDownLoadLink(mGameLink);
                    mHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            if (msg.what == 2) {
                                mFirstGameUrl = (String) msg.obj;
                                DownloadInfo downloadInfo = new DownloadInfo();
                                downloadInfo.setUrl(mFirstGameUrl);
                                downloadInfo.setGamePicUrl(gameDetail.getGameIcon());
                                Log.e("wei", "gameDetail.getGameIcon()"+gameDetail.getGameIcon());
                                downloadInfo.setAutoResume(true);
                                downloadInfo.setAutoRename(false);
                                downloadInfo.setLabel(gameNameTitle);
                                downloadInfo.setFileSavePath(FileUtils.fileSavePath+gameNameTitle+".apk");
                                mViewHolder = new DownloadItemViewHolder(null,downloadInfo);
                                mViewHolder.toggleEvent(null);
                            }
                        }
                    };
                    mBtInstallGame.setTag(1);
                }else {
                    mViewHolder.toggleEvent(null);
                }
            }
        });
    }
    public class DownloadItemViewHolder extends DownloadViewHolder {


        public DownloadItemViewHolder(View view, DownloadInfo downloadInfo) {
            super(view, downloadInfo);
            refresh();
        }


        private void toggleEvent(View view) {
            DownloadState state = downloadInfo.getState();
            switch (state) {
                case WAITING:
                case STARTED:
                    downloadManager.stopDownload(downloadInfo);
                    break;
                case ERROR:
                case STOPPED:
                    try {
                        downloadManager.startDownload(
                                downloadInfo.getUrl(),
                                downloadInfo.getGamePicUrl(),
                                downloadInfo.getLabel(),
                                downloadInfo.getFileSavePath(),
                                downloadInfo.isAutoResume(),
                                downloadInfo.isAutoRename(),
                                this);
                    } catch (DbException ex) {
                        Toast.makeText(x.app(), "添加下载失败", Toast.LENGTH_LONG).show();
                    }
                    break;
                case FINISHED:
                    Toast.makeText(x.app(), "已经下载完成", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }


        @Override
        public void update(DownloadInfo downloadInfo) {
            super.update(downloadInfo);
            Log.e("wei", "update");
            refresh();
        }

        @Override
        public void onWaiting() {
            Log.e("wei", "onWaiting");
            refresh();

        }

        @Override
        public void onStarted() {
            Log.e("wei", "onStarted");
            refresh();
        }

        @Override
        public void onLoading(long total, long current) {
            Log.e("wei", "onLoading" + "total:" + total + "current" + current);

            refresh();
        }

        @Override
        public void onSuccess(File result) {
            Log.e("wei", "onSuccess");
            refresh();
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            Log.e("wei", "onError");
            refresh();
        }

        @Override
        public void onCancelled(Callback.CancelledException cex) {
            Log.e("wei", "onCancelled");
            refresh();
        }

        public void refresh() {
           // game.setText(downloadInfo.getLabel());
           // state.setText(downloadInfo.getState().toString());
            gameEnName.setText(downloadInfo.getLabel()+"---"+downloadInfo.getState().toString()+"---"+downloadInfo.getProgress());
            //mProgressBar.setProgress(downloadInfo.getProgress());
            mBtInstallGame.setProgress(downloadInfo.getProgress());
            mBtInstallGame.setVisibility(View.VISIBLE);
            mBtInstallGame.setText(x.app().getString(R.string.stop));
            DownloadState state = downloadInfo.getState();
            switch (state) {
                case WAITING:
                case STARTED:
                    mBtInstallGame.setText(x.app().getString(R.string.stop));
                    break;
                case ERROR:
                case STOPPED:
                    mBtInstallGame.setText(x.app().getString(R.string.start));
                    break;
                case FINISHED:
                    mBtInstallGame.setText("下载完成");
                    break;
                default:
                    mBtInstallGame.setText(x.app().getString(R.string.start));
                    break;
            }
        }
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
        if (!NetUtils.checkNetWorkIsAvailable(this)) {
            mLoadingLayout.setStatus(LoadingLayout.No_Network);//无网络
            return;
        }
        mLoadingLayout.setStatus(LoadingLayout.Loading);//加载中
    }

    private void initData() {
        mGameLink = getIntent().getStringExtra("gameLink");
        gameNameTitle = getIntent().getStringExtra("gameName");
        gameStar = getIntent().getFloatExtra("gameStar", 5);
        //无网络
        //成功
        //监听游戏介绍显示方式
        mHandler = new Handler() {
            private Boolean mState = false;

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1 && gameDetail != null) {
                    if (!NetUtils.checkNetWorkIsAvailable(Game10Activity.this)) {
                        mLoadingLayout.setStatus(LoadingLayout.No_Network);//无网络
                        return;
                    } else {
                        mLoadingLayout.setStatus(LoadingLayout.Success);//成功
                    }
                    gamePics.addAll(gameDetail.getGamePic());
                    gameDetailPicAdapter.notifyDataSetChanged();
                    Glide.with(Game10Activity.this).load(gameDetail.getGameIcon()).into(gamePic);
                    Glide.with(Game10Activity.this).load(gameDetail.getGameRecommendImage().get(0)).into(gamePic1);
                    Glide.with(Game10Activity.this).load(gameDetail.getGameRecommendImage().get(1)).into(gamePic2);
                    Glide.with(Game10Activity.this).load(gameDetail.getGameRecommendImage().get(2)).into(gamePic3);
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
                            if (mState) {
                                mState = false;
                                textView7.setEllipsize(TextUtils.TruncateAt.END);
                                textView7.setMaxLines(3);
                                ObjectAnimator animator3 = ObjectAnimator.ofFloat(gamePic4, "rotation", 180f, 360f);
                                animator3.setDuration(500).start();
                            } else {
                                mState = true;
                                textView7.setEllipsize(null);
                                textView7.setMaxLines(Integer.MAX_VALUE);
                                ObjectAnimator animator3 = ObjectAnimator.ofFloat(gamePic4, "rotation", 0f, 180f);
                                animator3.setDuration(500).start();
                            }
                        }
                    });
                    textView7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (mState) {
                                mState = false;
                                textView7.setEllipsize(TextUtils.TruncateAt.END);
                                textView7.setMaxLines(3);
                                ObjectAnimator animator3 = ObjectAnimator.ofFloat(gamePic4, "rotation", 180f, 360f);
                                animator3.setDuration(500).start();
                            } else {
                                mState = true;
                                textView7.setEllipsize(null);
                                textView7.setMaxLines(Integer.MAX_VALUE);
                                ObjectAnimator animator3 = ObjectAnimator.ofFloat(gamePic4, "rotation", 0f, 180f);
                                animator3.setDuration(500).start();
                            }
                        }
                    });


                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                gameDetail = DataParser.getGameDetail(mGameLink);
                if (gameDetail != null) {
                    mHandler.obtainMessage(1).sendToTarget();
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
                        new ShareAction(Game10Activity.this).withText("hello")
                                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ,
                                        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.EMAIL, SHARE_MEDIA.SMS)
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
            Log.d("plat", "platform" + platform);

            Toast.makeText(Game10Activity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(Game10Activity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(Game10Activity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取游戏下载链接
     */
    public String getGameDownLoadLink(String link) {
        final String[] mPkgUrl = new String[1];
        final int[] lastIndexOf = {link.lastIndexOf("/")};
        String substring = link.substring(lastIndexOf[0]);
        int i1 = substring.indexOf(".");
        String s = substring.substring(0, i1);
        // LG.e(s + "haha");
        OkHttpUtils.get().url("http://android.d.cn/rm/red/1" + s).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    if (response != null) {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray pkgs = jsonObject.getJSONArray("pkgs");
                        mPkgUrl[0] = pkgs.getJSONObject(0).getString("pkgUrl");
                        Log.e("gameurl", mPkgUrl[0] + "");
                        mHandler.obtainMessage(2, mPkgUrl[0]).sendToTarget();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        return mPkgUrl[0];
    }
}
