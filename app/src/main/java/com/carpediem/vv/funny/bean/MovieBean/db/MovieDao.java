package com.carpediem.vv.funny.bean.MovieBean.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.carpediem.vv.funny.bean.MovieBean.MovieInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2016/12/3.
 */

public class MovieDao {
    private DbHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public MovieDao(Context context) {
        dbHelper = new DbHelper(context,1);
    }

    public void insert(MovieInfo movieInfo) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("image", movieInfo.getImage());
        contentValues.put("url", movieInfo.getUrl());
        contentValues.put("title", movieInfo.getTitle());
        contentValues.put("actor", movieInfo.getActor());
        contentValues.put("area", movieInfo.getArea());
        contentValues.put("date", movieInfo.getDate());
        contentValues.put("type", movieInfo.getType());
        sqLiteDatabase.insert("t_movies", null, contentValues);
        sqLiteDatabase.close();
    }

    public List<MovieInfo> findAll() {
        List<MovieInfo> movies = new ArrayList<>();
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from t_movies", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                MovieInfo movieInfo = new MovieInfo(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getColumnName(5),
                        cursor.getString(6),
                        cursor.getString(7));
                movieInfo.set_id(cursor.getInt(0));
                movies.add(movieInfo);
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return movies;
    }

    public void delete(int id) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.delete("t_movies", "_id=?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }
    public int had(String url){
        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from t_movies where url=?",new String[]{url});
        if(cursor.getCount()>0){
            cursor.moveToNext();
            int i = cursor.getInt(0);
            cursor.close();
            return i;
        }
        return -1;
    }
}
