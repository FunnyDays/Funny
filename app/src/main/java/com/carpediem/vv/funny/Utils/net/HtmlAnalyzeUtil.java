package com.carpediem.vv.funny.Utils.net;

import android.os.Handler;
import android.os.Message;


import com.carpediem.vv.funny.Utils.UrlUtil;
import com.carpediem.vv.funny.bean.MovieBean.MovieInfo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016/11/29.
 */

public class HtmlAnalyzeUtil {
    /**
     * 获取并解析电影列表
     *
     * @param id       电影类别
     * @param page     页码
     * @param callBack 回调
     */
    public static void analyzeMovieList(final int id, final int page, final HtmlCallBack callBack) {
        final List<MovieInfo> movies = new ArrayList<MovieInfo>();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                callBack.callBack(movies);
            }
        };
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String pageUrl = UrlUtil.getClassesUrl(id, page);
//                    Log.i(TAG, "url: " + pageUrl);
                    Document doc = Jsoup.connect(pageUrl).timeout(60000).get();
                    Elements es = doc.body().getElementsByClass("movList4");
                    for (Element e : es) {
//                        Log.i(TAG, "run: " + e.toString());
                        String img = e.getElementById("img_movie_100x140_0").attr("src");
                        Elements elements = e.getElementsByTag("h3").first().getAllElements();
                        String url = elements.get(1).attr("href");
                        String title = elements.get(1).text();
                        String date = "未知年代";
                        if (elements.size() > 2)
                            date = elements.get(2).text();
                        Elements e1 = e.getElementsByClass("playactor").first().getAllElements();
                        e1.remove(0);
                        StringBuilder actor = new StringBuilder();
                        for (Element e2 : e1) {
                            actor.append(e2.text()).append("\n");
                        }
                        Elements elements1 = e.getElementsByTag("ul");
                        Element element = elements1.get(1);
                        Elements elements2 = element.getElementsByTag("li");
                        String type = elements2.get(1).text().split(":")[1];
                        String area = elements2.get(3).text().split(":")[1];
                        MovieInfo movie = new MovieInfo(img, XunleicangInterface.HOST + url, title, actor.toString(), area, date, type);
                        movies.add(movie);
                    }
                    handler.sendEmptyMessage(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 根据关键字搜索并解析列表
     *
     * @param keyWords
     * @param page
     * @param callBack
     */
    public static void analyzeMovieList(final String keyWords, final int page, final HtmlCallBack callBack) {
        final List<MovieInfo> movies = new ArrayList<MovieInfo>();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                callBack.callBack(movies);
            }
        };
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String pageUrl = UrlUtil.getClassesUrl(keyWords, page);
//                    Log.i("lgst", "url: " + pageUrl);
                    Document doc = Jsoup.connect(pageUrl).timeout(6000).get();
                    Element pagesElement = doc.body().getElementsByClass("pages")//获取页码最大值
                            .first();
                    if (pagesElement == null) {
                        handler.sendEmptyMessage(1);
                        return;
                    }
                    String max = pagesElement.getElementsByTag("strong").first().text().split("/")[1];
                    if (page > Integer.parseInt(max)) {
                        handler.sendEmptyMessage(1);
                        return;
                    }
                    Elements es = doc.body().getElementsByClass("movList4");//电影列表
                    for (Element e : es) {
//                        Log.i(TAG, "run: " + e.toString());
                        String img = e.getElementById("img_movie_100x140_0").attr("data-original");
                        Elements elements = e.getElementsByTag("h3").first().getAllElements();
                        String url = elements.get(1).attr("href");
                        String title = elements.get(1).text();
                        String date = "未知年代";
                        if (elements.size() > 2)
                            date = elements.get(2).text();
                        String actor = e.getElementsByClass("playactor").first().text();
                        actor = actor.substring(actor.indexOf(":") + 1, actor.length()).replaceAll("[,]", "\n");
                        Elements elements1 = e.getElementsByTag("ul");
                        Element element = elements1.get(1);
                        Elements elements2 = element.getElementsByTag("li");
                        String type = elements2.get(1).text().split(":")[1];
                        String area = elements2.get(3).text().split(":")[1];
                        MovieInfo movie = new MovieInfo(img, XunleicangInterface.HOST + url, title, actor, area, date, type);
                        movies.add(movie);
                    }
                    handler.sendEmptyMessage(1);
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();
    }

    public interface HtmlCallBack {
        void callBack(List<MovieInfo> movies);
    }
}
