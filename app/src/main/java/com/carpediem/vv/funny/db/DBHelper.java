package com.carpediem.vv.funny.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/12/1.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "download.db";
    public static final int VERSION = 1;
    public static final String SQL_CREATE = "create table thread_info(_id integer primary key autoincrement," +
            "thread_id integer,url text,icon text,name text,start integer,end integer,finished integer,file_length integer)";
    public static final String SQL_DROP = "drop table if exists thread_info";

    private static DBHelper mHelper=null;

    public static DBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper=new DBHelper(context);
        }
        return mHelper;
    }
    private DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }
}
