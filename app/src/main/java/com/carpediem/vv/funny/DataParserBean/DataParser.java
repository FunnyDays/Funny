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
    public static ArrayList<GameDetail> getGameDetail(String link) {
        ArrayList<GameDetail> gameArrayList = new ArrayList<>();
        GameDetail gameDetail =null;
        try {
            Document doc = Jsoup.connect(link).get();
            Elements element = doc.select("div.gameimg-screen").select("img");
            for (int i = 0; i <element.size() ; i++) {
                 gameDetail = new GameDetail();
                //获取游戏链接
                String gameLink = element.get(i).attr("src");
                gameDetail.setGamePic(gameLink);
                gameArrayList.add(gameDetail);
                Log.e("weiwei", "游戏名称：" + element.size() + "图片的链接地址"+gameLink);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameArrayList;
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
