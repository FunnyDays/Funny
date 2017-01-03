package com.carpediem.vv.funny.db;


import com.carpediem.vv.funny.bean.downLoad.ThreadInfo;

import java.util.List;

/**
 * 数据访问接口
 * Created by Administrator on 2016/12/1.
 */

public interface ThreadDao {
    /**
     * 插入线程信息
     *
     * @param threadInfo
     */
    public void insertThread(ThreadInfo threadInfo);

    /**
     * 删除线程
     *
     * @param url
     */
    public void deleteThread(String url);

    /**
     * 更新线程下载进度
     *
     * @param url
     * @param thread_id
     * @param finished
     */
    public void updateThread(String url, int thread_id, long finished);
    /**
     * 设置暂停记录
     *
     * @param url
     * @param thread_id
     * @param pause
     */
    public void setPauseThread(String url, int thread_id,int pause);

    /**
     * 查询文件线程信息
     *
     * @param url
     * @return
     */
    public List<ThreadInfo> getThreads(String url);

    /**
     * 根据文件名称查询文件信息
     *
     * @param name
     * @return
     */
    public List<ThreadInfo> getThreadsByName(String name);

    /**
     * 查询所有文件信息
     *
     * @return
     */
    public List<ThreadInfo> getAllThreadsByName();

    /**
     * 查询线程信息是否存在
     *
     * @param url
     * @param thread_id
     * @return
     */
    public boolean isExists(String url, int thread_id);
}
