package com.carpediem.vv.funny.bean.MovieBean.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LG on 2016/12/3.
 */

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context, int version) {
        super(context, "t_movies",null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table t_movies(_id integer primary key autoincrement , image varchar(500) , url varchar(500) , title varchar(100) , actor varchar(150) , area varchar(20) , date varchar(20) , type varchar(20))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
