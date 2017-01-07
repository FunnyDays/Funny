package com.carpediem.vv.funny.services;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


import com.carpediem.vv.funny.bean.download.FileInfo;
import com.carpediem.vv.funny.db.ThreadDao;
import com.carpediem.vv.funny.db.ThreadDaoImpl;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/1.
 */

public class DownLoadServices extends Service {
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DownLoadgames/";
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_CANCEL = "ACTION_CANCEL";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String ACTION_FINISH = "ACTION_FINISH";
    //初始化标识
    private static final int MSG_INIT = 0x1;
    //网络出错标识
    private static final int MSG_ERROR = 0x2;
    //下载任务集合
    public static Map<String, DownloadTask> mTasks = new LinkedHashMap<String , DownloadTask>();
    private InitThread mInitThread;
    //从数据库中获取线程信息
    private ThreadDao threadDao = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            switch (intent.getAction()) {
                case ACTION_START:
                    FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
                    //启动初始化线程
                    mInitThread = new InitThread(fileInfo);
                    DownloadTask.executorService.execute(mInitThread);
                    Log.e("download", "start" + fileInfo.toString());
                    break;
                case ACTION_STOP:
                    FileInfo mfileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
                    Log.e("download", "stop" + mfileInfo.toString());
                    //从集合中取出下载任务
                    DownloadTask task = mTasks.get(mfileInfo.getUrl());
                    if (task != null) {
                        task.isPause = true;
                    }
                    break;
                case ACTION_CANCEL:
                    FileInfo myfileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
                    //从集合中移除下载任务
                    DownloadTask remove = mTasks.remove(myfileInfo.getUrl());
                    if (remove != null) {
                        remove.isPause = true;
                    }
                    //删除数据库中的下载数据
                    ThreadDaoImpl threadDao = new ThreadDaoImpl(this);
                    threadDao.deleteThread(myfileInfo.getUrl());
                    Log.e("download", "cancel" + myfileInfo.toString());
                    break;
            }

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    Log.e("download", "init:" + fileInfo);
                    //启动下载任务
                    DownloadTask task = new DownloadTask(DownLoadServices.this, fileInfo, 1);
                    task.download();
                    mTasks.put(fileInfo.getUrl(), task);
                    break;
                case MSG_ERROR:
                    Toast.makeText(DownLoadServices.this, "下载网络错误，请稍后重试", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    /**
     * 初始化子线程
     */
    class InitThread extends Thread {
        private FileInfo fileInfo = null;

        public InitThread(FileInfo fileInfo) {
            this.fileInfo = fileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection urlConnection = null;
            RandomAccessFile randomAccessFile = null;
            try {
                //链接网络文件
                URL url = new URL(fileInfo.getUrl());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(3000);
                urlConnection.setRequestMethod("GET");
                //获取文件长度
                int length = -1;
                if (urlConnection.getResponseCode() == 200) {
                    length = urlConnection.getContentLength();
                }
                if (length <= 0) {
                    return;
                }
                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                //在本地创建文件
                File file = new File(dir, fileInfo.getFileName());
                randomAccessFile = new RandomAccessFile(file, "rwd");
                //设置长度
                randomAccessFile.setLength(length);
                fileInfo.setLength(length);
                handler.obtainMessage(MSG_INIT, fileInfo).sendToTarget();
            } catch (Exception e) {
                Log.e("weiwei", "错误码是：" + e.toString());
                handler.obtainMessage(MSG_ERROR).sendToTarget();
            } finally {
                try {
                    urlConnection.disconnect();
                    randomAccessFile.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
