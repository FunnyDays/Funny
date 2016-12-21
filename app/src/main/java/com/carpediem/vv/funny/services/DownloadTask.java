package com.carpediem.vv.funny.services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.carpediem.vv.funny.bean.downLoad.FileInfo;
import com.carpediem.vv.funny.bean.downLoad.ThreadInfo;
import com.carpediem.vv.funny.db.ThreadDao;
import com.carpediem.vv.funny.db.ThreadDaoImpl;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/12/1.
 */

public class DownloadTask {
    private Context context = null;
    private FileInfo fileInfo = null;
    private ThreadDao threadDao = null;
    private long finished = 0;
    public Boolean isPause = false;
    private int mThreadCount = 1;
    private ArrayList<DownloadThread> mDownloadThreads;
    //线程池实现
    public static ExecutorService executorService = Executors.newCachedThreadPool();

    private Timer mTimer = new Timer();
    ThreadInfo mThreadInfo = null;
    private long mFileLength;


    public DownloadTask(Context context, FileInfo fileInfo, int mThreadCount) {
        this.context = context;
        this.fileInfo = fileInfo;
        this.mThreadCount = mThreadCount;
        threadDao = new ThreadDaoImpl(context);
    }

    public  void download() {
        //读取数据库的线程信息
        List<ThreadInfo> threads = threadDao.getThreads(fileInfo.getUrl());
        if (threads.size() == 0) {
            //获得每个线程下载长度
            int length = fileInfo.getLength() / mThreadCount;
            for (int i = 0; i < mThreadCount; i++) {
                //创建线程信息
                mThreadInfo = new ThreadInfo(i, fileInfo.getUrl(), length * i, (i + 1) * length - 1, 0);
                if (mThreadCount - 1 == i) {
                    mThreadInfo.setEnd(fileInfo.getLength());
                    mFileLength = fileInfo.getLength();
                }
                //把文件大小设置到每个线程里面
                mThreadInfo.setLength(fileInfo.getLength());
                //添加到线程信息集合中
                threads.add(mThreadInfo);
                //向数据库插入线程信息
                Log.e("weiwei1", "向数据库插入线程信息:");
                threadDao.insertThread(mThreadInfo);
            }
        } else {
            mFileLength = threads.get(0).getLength();
        }
        mDownloadThreads = new ArrayList<>();
        //启动多个线程下载
        for (ThreadInfo info : threads) {
            DownloadThread downloadThread = new DownloadThread(info);
            // downloadThread.start();
            DownloadTask.executorService.execute(downloadThread);
            //添加到线程集合中
            mDownloadThreads.add(downloadThread);
        }
        //启动定时任务
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(DownLoadServices.ACTION_UPDATE);
                intent.putExtra("finished", (int) (finished * 100 / mFileLength));
                intent.putExtra("id", fileInfo.getId());
                context.sendBroadcast(intent);
            }
        }, 1000, 1000);

    }

    /**
     * 判断所有线程是否执行完毕
     */
    public synchronized void checkAllThreadsFinished() {
        boolean allThreads = true;
        for (DownloadThread thread : mDownloadThreads) {
            if (!thread.isFinished) {
                allThreads = false;
                break;
            }
        }
        if (allThreads) {
            //取消定时器
            mTimer.cancel();
            //删除线程信息
            threadDao.deleteThread(fileInfo.getUrl());
            //发送广播通知UI下载完成
            Intent intent = new Intent(DownLoadServices.ACTION_FINISH);
            intent.putExtra("fileInfo", fileInfo);
            context.sendBroadcast(intent);
        }
    }

    /**
     * 下载线程
     */
    class DownloadThread extends Thread {
        private ThreadInfo threadInfo = null;
        public boolean isFinished = false;//标识线程是否执行完毕

        public DownloadThread(ThreadInfo threadInfo) {
            this.threadInfo = threadInfo;
        }

        @Override
        public void run() {
            //设置下载位置
            HttpURLConnection urlConnection = null;
            RandomAccessFile randomAccessFile = null;
            InputStream inputStream = null;
            try {
                URL url = new URL(threadInfo.getUrl());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(3000);
                urlConnection.setRequestMethod("GET");
                //下载位置
                long start = threadInfo.getStart() + threadInfo.getFinished();
                Log.e("wdownload", "每个线程下载的开始位置:" + start + "结束位置：" + threadInfo.getEnd());
                urlConnection.setRequestProperty("Range", "bytes=" + start + "-" + threadInfo.getEnd());
                //设置文件写入位置
                File file = new File(DownLoadServices.DOWNLOAD_PATH, fileInfo.getFileName());
                randomAccessFile = new RandomAccessFile(file, "rwd");
                randomAccessFile.seek(start);
                finished += threadInfo.getFinished();
                //开始下载
                if (urlConnection.getResponseCode() == 206) {
                    //读取数据
                    inputStream = urlConnection.getInputStream();
                    byte[] buffer = new byte[1024 * 1024];
                    int len = -1;
                    while ((len = inputStream.read(buffer)) != -1) {
                        //写入文件
                        randomAccessFile.write(buffer, 0, len);
                        //累加文件完成进度
                        finished += len;
                        //累加每个线程完成进度
                        threadInfo.setFinished(threadInfo.getFinished() + len);
                        //在下载暂停时，保存进度到数据库
                        threadDao.updateThread(threadInfo.getUrl(), threadInfo.getId(), threadInfo.getFinished());
                        if (isPause) {
                            mTimer.cancel();
                            Log.e("download", "暂停时文件已下载大小:" + finished);
                            return;
                        }
                    }
                    //标识线程是否执行完毕
                    isFinished = true;
                    //检查下载任务是否执行完毕
                    checkAllThreadsFinished();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    urlConnection.disconnect();
                    randomAccessFile.close();
                    inputStream.close();
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }
    }
}
