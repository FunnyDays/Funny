package com.carpediem.vv.funny.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.Utils.AppUtils;

import com.carpediem.vv.funny.bean.download.FileInfo;
import com.carpediem.vv.funny.services.DownLoadServices;
import com.carpediem.vv.funny.services.DownloadTask;

import java.util.List;

/**
 * Created by Administrator on 2016/12/22.
 */
public class DownloadAdapter extends RecyclerView.Adapter {
    private Activity mActivity;
    private List<FileInfo> mFileList;
    boolean mState = false;


    public DownloadAdapter(Activity activity, List<FileInfo> list) {
        mActivity = activity;
        mFileList = list;

    }

    /**
     * recyclevivew的点击接口回调
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.mOnItemClickLitener = onItemClickLitener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_download_app_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final boolean[] mDownloading = {false};
        if (holder instanceof ItemViewHolder) {
            final FileInfo fileInfo=mFileList.get(position);
            ((ItemViewHolder) holder).mAppName.setText(mFileList.get(position).getFileName());
            Glide.with(mActivity).load(mFileList.get(position).getIcon()).into(((ItemViewHolder) holder).mAppIcon);
            if (mOnItemClickLitener != null){
                ((ItemViewHolder)holder).mBtDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickLitener.onItemClick(((ItemViewHolder)holder).mBtDownload,position);
                    }
                });
            }
            ((ItemViewHolder) holder).mProgressBar.setMax(100);
           ((ItemViewHolder) holder).mProgressBar.setProgress(fileInfo.getLength());
            DownloadTask downloadTask = DownLoadServices.mTasks.get(mFileList.get(position).getUrl());
            if (downloadTask != null) {
                mDownloading[0] = downloadTask.isDownloading();
                if (mDownloading[0]){
                    ((ItemViewHolder) holder).mBtDownload.setText("暂停");
                }
            }
            ((ItemViewHolder) holder).mBtDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDownloading[0]){
                        mDownloading[0] =false;
                        ((ItemViewHolder) holder).mBtDownload.setText("下载");
                        Intent intent = new Intent(mActivity, DownLoadServices.class);
                        intent.setAction(DownLoadServices.ACTION_STOP);
                        intent.putExtra("fileInfo",fileInfo);
                        mActivity.startService(intent);
                    }else {
                        mDownloading[0] =true;
                        ((ItemViewHolder) holder).mBtDownload.setText("暂停");
                        Intent intent = new Intent(mActivity, DownLoadServices.class);
                        intent.setAction(DownLoadServices.ACTION_START);
                        intent.putExtra("fileInfo",fileInfo);
                        mActivity.startService(intent);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mFileList == null ? 0 : mFileList.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView mAppName;
        private final ImageView mAppIcon;
        private final Button mBtDownload;
        private final ProgressBar mProgressBar;

        public ItemViewHolder(View view) {
            super(view);
            mAppIcon = (ImageView) view.findViewById(R.id.app_pic);
            mAppName = (TextView) view.findViewById(R.id.app_name);
            mBtDownload = (Button) view.findViewById(R.id.bt_uninstall);
            mProgressBar = (ProgressBar) view.findViewById(R.id.pb_progressbar);
        }
    }
    /**
     * 更新列表中的进度条
     */
    public synchronized void updateProgress(String url,int progress){
        for (int i = 0; i < mFileList.size(); i++) {
            if (mFileList.get(i).getUrl().equals(url)) {
                mFileList.get(i).setLength(progress);
            }
        }
        notifyDataSetChanged();
    }

}
