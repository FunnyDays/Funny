package com.carpediem.vv.funny.bean.GameBean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/8.
 */

public class Game extends BmobObject {
    private String gameName;
    private String gamePic;
    private String gameEdition;
    private String gameSize;
    private String gameIntro;

    public String getGameStar() {
        return gameStar;
    }

    public void setGameStar(String gameStar) {
        this.gameStar = gameStar;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGamePic() {
        return gamePic;
    }

    public void setGamePic(String gamePic) {
        this.gamePic = gamePic;
    }

    public String getGameEdition() {
        return gameEdition;
    }

    public void setGameEdition(String gameEdition) {
        this.gameEdition = gameEdition;
    }

    public String getGameSize() {
        return gameSize;
    }

    public void setGameSize(String gameSize) {
        this.gameSize = gameSize;
    }

    public String getGameIntro() {
        return gameIntro;
    }

    public void setGameIntro(String gameIntro) {
        this.gameIntro = gameIntro;
    }

    private String gameStar;

}
