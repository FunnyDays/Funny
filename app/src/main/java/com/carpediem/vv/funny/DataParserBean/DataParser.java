package com.carpediem.vv.funny.DataParserBean;


import android.util.Log;

import com.carpediem.vv.funny.Utils.net.DangLeInterface;
import com.carpediem.vv.funny.bean.GameBean.Game;
import com.carpediem.vv.funny.bean.GameBean.GameDetail;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/11.
 */

public class DataParser {
    public static DataParser getInstance() {
        return new DataParser();
    }

    /**
     * 获取五张游戏图片
     * @param link
     * @return
     */
    public static GameDetail getGameDetail(String link) {
        GameDetail gameDetail = new GameDetail();
        ArrayList<String> picArrayList= new ArrayList<>();
        try {
            Document doc = Jsoup.connect(link).get();
            //获取游戏icon
            String appIcon = doc.select("div.de-head-l").select("img").attr("src");
            gameDetail.setGameIcon(appIcon);
            //获取游戏名称（英文和中文）de-app-des
            String appName = doc.select("h1.notag").text();
            String appEnName = doc.select("h2.de-app-en.notag-h2").text();
            gameDetail.setGameName(appName);
            gameDetail.setGameEnName(appEnName);
            //游戏下载链接(网站是动态加载的，获取不到链接，以后再说)
            //String appDownLoad = doc.select("div.de-has-set.clearfix").select("a").outerHtml();


            //获取游戏图片链接
            Elements element1 = doc.select("div.gameimg-screen").select("img");
            for (int i = 0; i <element1.size() ; i++) {
                picArrayList.add(element1.get(i).attr("src"));
               Log.e("weiwei", "游戏名称：" + element1.size() + "图片的链接地址"+picArrayList.get(i));
            }
            gameDetail.setGamePic(picArrayList);
            //获取游戏信息
            String gameType = doc.select("li.de-game-firm").select("a").text();
            String allGameType = doc.select("ul.de-game-info.clearfix").text();
            Elements elements = doc.select("ul.sim-app");
            String string = elements.toString();
            for (int i = 0; i < elements.size(); i++) {
               // String s = elements.select("a").attr("title");
                Log.e("weiwei", "数量一共是"+elements.size()+"游戏名称：" );
            }

            Log.e("weiwei", string+"游戏名称：" +appIcon+appName+appEnName + "图片的链接地址"+allGameType+gameType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameDetail;
    }

    /**
     * 获取30条游戏列表信息
     * @param j
     * @return
     */
    public static ArrayList<Game> getAllGame(int j) {
        ArrayList<Game> gameArrayList = new ArrayList<>();
        Game game;
        try {
            Document doc = Jsoup.connect(DangLeInterface.getGameInterface(j)).get();
            Elements element = doc.select("div.list-in");

            for (int i = 0; i < element.size(); i++) {
                game = new Game();
                //获取游戏名称
                String name = element.get(i).select("div.list-left").select("a").attr("title");
                //获取游戏链接
                String link = element.get(i).select("div.list-left").select("a").attr("href");
                //获取游戏图标
                String pic = element.get(i).select("div.list-left").select("a").select("img").attr("o-src");
                //获取星星数量
                String starts = element.get(i).select("div.list-left").select(".stars.iconSprite").attr("class");
                //获取游戏版本
                String edtion = element.get(i).select("div.list-right").select("p.down-ac").text();
                //获取游戏介绍
                String intro = element.get(i).select("div.list-right").select("p.g-intro").text();
                //获取游戏大小
                String size = element.get(i).select("div.list-right").select("p.g-detail").text();
                game.setGameEdition(edtion);
                game.setGameSize(size);
                game.setGameIntro(intro);
                game.setGameStar(Float.valueOf(starts.substring(starts.length() - 1)));
                game.setGameName(name);
                game.setGamePic(pic);
                game.setGameDetailLink(link);
                gameArrayList.add(game);

               // Log.e("weiwei", "游戏名称：" + gameArrayList.get(i).getGameName() + "游戏的链接地址" + gameArrayList.get(i).getGameDetailLink());
            }


           // Log.e("weiwei", "游戏名称：" + element.size() + "图片的链接地址");


        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameArrayList;
    }

}
