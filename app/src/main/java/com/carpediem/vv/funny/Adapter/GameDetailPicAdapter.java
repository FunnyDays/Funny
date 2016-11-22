package com.carpediem.vv.funny.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.carpediem.vv.funny.R;


import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/22.
 */
public class GameDetailPicAdapter extends RecyclerView.Adapter {
    private Activity mActivity;
    private ArrayList arrayList;
    public GameDetailPicAdapter(Activity mActivity) {
        this.mActivity=mActivity;
    }
    /**
     * ItemClick的回调接口
     * @author zhy
     *
     */
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_viewpaget_image, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        /*if (arrayList.size() == 0) {

        } else */if (holder instanceof ItemViewHolder) {
            Glide.with(mActivity)
                    .load(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(((ItemViewHolder) holder).imageView);

        }
        //如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickLitener.onItemClick(holder.itemView, position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
     class ItemViewHolder extends RecyclerView.ViewHolder{

         private final ImageView imageView;

         public ItemViewHolder(View itemView) {
             super(itemView);
             imageView = (ImageView) itemView.findViewById(R.id.image);
         }
     }
}
