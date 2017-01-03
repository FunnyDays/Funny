package com.carpediem.vv.funny.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.carpediem.vv.funny.bean.downLoad.ThreadInfo;

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
        db.execSQL("insert into thread_info(file_id,thread_id,url,file_name,file_icon,start,end,finished,file_length,is_pause) values(?,?,?,?,?,?,?,?,?,?)",
                new Object[]{threadInfo.getFileId(),threadInfo.getId(), threadInfo.getUrl(),threadInfo.getName(),threadInfo.getIcon(), threadInfo.getStart(), threadInfo.getEnd(), threadInfo.getFinished(),threadInfo.getLength(),threadInfo.getPause()});
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
    public void setPauseThread(String url, int thread_id, int pause) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("update thread_info set is_pause =? where url=? and thread_id=?",
                new Object[]{pause, url, thread_id});
        db.close();
    }

    /**
     * 根据url获取线程信息
     * @param url
     * @return
     */
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
            threadInfo.setPause(cursor.getInt(cursor.getColumnIndex("is_pause")));
            threadInfos.add(threadInfo);
        }
        cursor.close();
        db.close();
        return threadInfos;
    }
    /**
     * 根据文件名称获取所有下载信息
     * @param name
     * @return
     */
    @Override
    public synchronized List<ThreadInfo> getThreadsByName(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<ThreadInfo> threadInfos = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from thread_info where file_name=?", new String[]{name});
        while (cursor.moveToNext()){
            ThreadInfo threadInfo = new ThreadInfo();
            threadInfo.setFileId(cursor.getInt(cursor.getColumnIndex("file_id")));
            threadInfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadInfo.setName(cursor.getString(cursor.getColumnIndex("file_name")));
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
    public List<ThreadInfo> getAllThreadsByName() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<ThreadInfo> threadInfos = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from thread_info where thread_id=?", new String[]{"0"});
        while (cursor.moveToNext()){
            ThreadInfo threadInfo = new ThreadInfo();
            threadInfo.setFileId(cursor.getInt(cursor.getColumnIndex("file_id")));
            threadInfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadInfo.setName(cursor.getString(cursor.getColumnIndex("file_name")));
            threadInfo.setIcon(cursor.getString(cursor.getColumnIndex("file_icon")));
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
