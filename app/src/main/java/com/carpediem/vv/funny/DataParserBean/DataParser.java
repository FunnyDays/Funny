package com.carpediem.vv.funny.DataParserBean;

import android.util.Log;


import com.carpediem.vv.funny.Utils.net.DangLeInterface;
import com.carpediem.vv.funny.bean.GameBean.Game;

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
    public static ArrayList<Game> getAllGame(int j){
        ArrayList<Game> gameArrayList = new ArrayList<>();
        Game game;
        try {
            Document doc = Jsoup.connect(DangLeInterface.getGameInterface(j)).get();
            Elements element = doc.select("div.list-in");

            for (int i = 0; i <element.size(); i++) {
                game = new Game();
                //获取游戏名称
                String name = element.get(i).select("div.list-left").select("a").attr("title");
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
                game.setGameStar(Float.valueOf(starts.substring(starts.length()-1)));
                game.setGameName(name);
                game.setGamePic(pic);
                gameArrayList.add(game);

                //  Log.e("weiwei", "游戏名称：" + gameArrayList.get(i).getGameName() + "图片的链接地址" + gameArrayList.get(i).getGamePicLink());
            }


            Log.e("weiwei", "游戏名称：" + element.size() + "图片的链接地址" );


        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameArrayList;
    }

}
