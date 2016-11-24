package com.carpediem.vv.funny.bean.GameBean;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/22.
 */
public class GameDetail {
    String gameIcon;
    String gameName;
    String gameEnName;
    ArrayList<String> gamePic;
    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameEnName() {
        return gameEnName;
    }

    public void setGameEnName(String gameEnName) {
        this.gameEnName = gameEnName;
    }

    public String getGameIcon() {
        return gameIcon;
    }

    public void setGameIcon(String gameIcon) {
        this.gameIcon = gameIcon;
    }

    public ArrayList<String> getGamePic() {
        return gamePic;
    }

    public void setGamePic(ArrayList<String> gamePic) {
        this.gamePic = gamePic;
    }



}
