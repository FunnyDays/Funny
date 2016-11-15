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
            Elements element = doc.select("div.list-left");
            String attr = element.select("a").attr("title");
            for (int i = 0; i <element.size(); i++) {
                game = new Game();
                //获取游戏名称
                String name = element.get(i).select("a").attr("title");
                //获取游戏图标
                String pic = element.get(i).select("a").select("img").attr("o-src");
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
