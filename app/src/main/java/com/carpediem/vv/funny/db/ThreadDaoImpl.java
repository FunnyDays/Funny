package com.carpediem.vv.funny.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.carpediem.vv.funny.bean.download.ThreadInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */

public class ThreadDaoImpl implements ThreadDao {
    private DBHelper dbHelper = null;

    public ThreadDaoImpl(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    @Override
    public synchronized void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("insert into thread_info(thread_id,url,icon,name,start,end,finished,file_length) values(?,?,?,?,?,?,?,?)",
                new Object[]{threadInfo.getId(), threadInfo.getUrl(),threadInfo.getIcon(),threadInfo.getName(), threadInfo.getStart(), threadInfo.getEnd(), threadInfo.getFinished(),threadInfo.getLength()});
        db.close();
    }

    @Override
    public synchronized void deleteThread(String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from thread_info where url=?",
                new Object[]{url});
        db.close();
    }

    @Override
    public synchronized void updateThread(String url, int thread_id, long finished) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("update thread_info set finished =? where url=? and thread_id=?",
                new Object[]{finished, url, thread_id});
       // db.close();
    }

    @Override
    public synchronized List<ThreadInfo> getThreads(String url) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<ThreadInfo> threadInfos = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from thread_info where url=?", new String[]{url});
        while (cursor.moveToNext()){
            ThreadInfo threadInfo = new ThreadInfo();
            threadInfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadInfo.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            threadInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            threadInfo.setLength(cursor.getInt(cursor.getColumnIndex("file_length")));
            threadInfos.add(threadInfo);
        }
        cursor.close();
        db.close();
        return threadInfos;
    }

    @Override
    public synchronized boolean isExists(String url, int thread_id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from thread_info where url=? and thread_id=?", new String[]{url,thread_id+""});
        boolean exists=cursor.moveToNext();
        cursor.close();
        db.close();
        return exists;
    }
}
