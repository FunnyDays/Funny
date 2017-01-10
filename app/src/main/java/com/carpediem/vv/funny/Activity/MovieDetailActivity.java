package com.carpediem.vv.funny.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.Utils.Loading.Utils;
import com.carpediem.vv.funny.bean.MovieBean.DownloadInfo;
import com.carpediem.vv.funny.bean.MovieBean.MovieInfo;
import com.carpediem.vv.funny.bean.MovieBean.db.MovieDao;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener {

    private Toolbar mToolbar;
    private ImageView mImg;
    private TextView mTitle;
    private TextView mType;
    private LinearLayout mActors;
    private TextView mArea;
    private TextView mDate;
    private AppCompatTextView mTvDetail;
    private LinearLayout mDownload;
    private MovieInfo movieInfo;
    private static final String TAG = "lgst";
    private List<DownloadInfo> mDownUrls;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setData();
        }
    };
    private boolean isInstalled;
    private AppCompatTextView mXunlei;
    private MovieDao mDao;

    private void setData() {
        for (final DownloadInfo down : mDownUrls) {
            View v = getLayoutInflater().inflate(R.layout.item_download, null);
            ((TextView) v.findViewById(R.id.name)).setText(down.getName());
            v.findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    String url = down.getUrl().replace("thunder://", "");
//                    url = new String(Base64.decode(url.getBytes(), Base64.DEFAULT));
//                    url = url.substring(2, url.length() - 2);
                    if (isInstalled){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(down.getUrl()));
                        intent.addCategory("android.intent.category.DEFAULT");
                        startActivity(intent);
                    }else {
                        Toast.makeText(MovieDetailActivity.this, "请安装手机迅雷", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            mDownload.addView(v);
        }
        mTvDetail.setText(movieInfo.getDetail());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_movie_detail);
        movieInfo = (MovieInfo) getIntent().getSerializableExtra("movie");
        mDownUrls = new ArrayList<DownloadInfo>();
        checkXunlei();
        initView();
        initData();
    }

    private void checkXunlei() {
        PackageManager pm = getPackageManager();
        List<PackageInfo> pis = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo pi : pis) {
//            Log.i("lgst", "checkXunlei: "+pi.packageName);
            //12-01 08:36:41.076: I/lgst(5746): checkXunlei: com.xunlei.downloadprovider
            if (pi.packageName.equals("com.xunlei.downloadprovider")) {
                isInstalled = true;
                break;
            }
        }
    }

    private void initData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Document doc = Jsoup.connect(movieInfo.getUrl()).get();
                    Element body = doc.body();
                    Elements es = body.getElementsByClass("movie_story3");
                    Element e = es.first();
                    String str = e.toString();
                    str = str.replaceAll("<[^>]+>", "");
                    movieInfo.setDetail(str);
                    Elements downurl = body.getElementsByClass("downurl");
                    for (Element e2 : downurl) {
                        Elements urls = e2.getElementsByTag("a");
                        for (Element e1 : urls) {
                            String name = e1.text();
                            String url = e1.attr("href");
                            DownloadInfo dow = new DownloadInfo(url, name);
                            mDownUrls.add(dow);
                        }
                    }
                    mDownUrls.size();
                    mHandler.sendEmptyMessage(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mImg = (ImageView) findViewById(R.id.img);
        mTitle = (TextView) findViewById(R.id.title);
        mType = (TextView) findViewById(R.id.type);
        mActors = (LinearLayout) findViewById(R.id.actors);
        mArea = (TextView) findViewById(R.id.area);
        mDate = (TextView) findViewById(R.id.date);
        mTvDetail = (AppCompatTextView) findViewById(R.id.tv_detail);
        mDownload = (LinearLayout) findViewById(R.id.download);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mToolbar.setTitle(movieInfo.getTitle());
        mToolbar.inflateMenu(R.menu.menu_detail);
        mToolbar.setOnMenuItemClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            mToolbar.setPadding(0, Utils.dp2px(this, 24), 0, 0);
        mTitle.setText(movieInfo.getTitle());
        mArea.setText(movieInfo.getArea());
        String[] actors = movieInfo.getActor().split("[\n| ]");
        for (final String str : actors) {
            TextView tv = new TextView(this);
            tv.setText(str);
            tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  MainActivity ma = (MainActivity) VideoApplication.activities.get(0);
                   // ma.changeToFind(str);
                    //finish();
                }
            });
            mActors.addView(tv);
        }
        mDate.setText(movieInfo.getDate());
        mType.setText(movieInfo.getType());
        Glide.with(this)
                .load(movieInfo.getImage())
                .into(mImg);
        mXunlei = (AppCompatTextView) findViewById(R.id.xunlei);
        mXunlei.setOnClickListener(this);
        if (!isInstalled) {
            mXunlei.setVisibility(View.VISIBLE);
        }
        mDao = new MovieDao(this);
        int id = mDao.had(movieInfo.getUrl());
        if (id != -1) {
            mToolbar.getMenu().getItem(0).setIcon(R.drawable.ic_favorite_white);
            movieInfo.set_id(id);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.xunlei:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.xunlei.com/"));
                intent.addCategory("android.intent.category.DEFAULT");
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (mDao.had(movieInfo.getUrl()) != -1) {
            mDao.delete(movieInfo.get_id());
            Snackbar.make(mToolbar, "已取消收藏！", Snackbar.LENGTH_SHORT).show();
            mToolbar.getMenu().getItem(0).setIcon(R.drawable.ic_favorite_white_border);
        } else {
            mDao.insert(movieInfo);
            Snackbar.make(mToolbar, "收藏成功！", Snackbar.LENGTH_SHORT).show();
            mToolbar.getMenu().getItem(0).setIcon(R.drawable.ic_favorite_white);
        }
        return false;
    }
}
