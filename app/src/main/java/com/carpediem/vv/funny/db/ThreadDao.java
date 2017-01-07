package com.carpediem.vv.funny.db;


import com.carpediem.vv.funny.bean.download.ThreadInfo;

import java.util.List;

/**
 * 数据访问接口
 * Created by Administrator on 2016/12/1.
 */

public interface ThreadDao {
    /**
     * 插入线程信息
     * @param threadInfo
     */
    public void insertThread(ThreadInfo threadInfo);

    /**
     * 删除线程
     * @param url
     */
    public void deleteThread(String url);

    /**
     * 更新线程下载进度
     * @param url
     * @param thread_id
     * @param finished
     */
    public void updateThread(String url, int thread_id, long finished);

    /**
     * 查询文件线程信息
     * @param url
     * @return
     */
    public List<ThreadInfo> getThreads(String url);

    /**
     * 查询线程信息是否存在
     * @param url
     * @param thread_id
     * @return
     */
    public boolean isExists(String url, int thread_id);
}
