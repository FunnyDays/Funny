package com.carpediem.vv.funny.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.carpediem.vv.funny.Activity.CommentActivity;
import com.carpediem.vv.funny.Fragment.DailyFragment;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.bean.GameBean.Game;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/8.
 */

public class GameAdapter extends RecyclerView.Adapter {

    private static final int TYPE_ONE = 1;
    private static final int TYPE_FOOTER = 0;
    private ArrayList<Game> arrayList;
    private Activity mActivity;
    public GameAdapter(Activity mActivity, ArrayList<Game> arrayList) {
        this.arrayList=arrayList;
        this.mActivity=mActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case TYPE_FOOTER :
                view = LayoutInflater.from(mActivity).inflate(R.layout.item_foot, parent, false);
                return new FootViewHolder(view);
            case TYPE_ONE:
                view = LayoutInflater.from(mActivity).inflate(R.layout.item_game_view, parent, false);
                return new ItemViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(arrayList.size()==0){

        }else  if (holder instanceof ItemViewHolder) {
         ((ItemViewHolder)holder).gameEdition.setText(arrayList.get(position).getGameEdition());
         ((ItemViewHolder)holder).gameIntro.setText(arrayList.get(position).getGameIntro());
         ((ItemViewHolder)holder).gameName.setText(arrayList.get(position).getGameName());
         ((ItemViewHolder)holder).gameSize.setText(arrayList.get(position).getGameSize());
            Glide.with(mActivity)
                    .load(arrayList.get(position).getGamePic())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(((ItemViewHolder)holder).gamePic);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        }else {
            return TYPE_ONE;
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size() == 0 ? 10 : arrayList.size() + 1;
    }
    class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }
    class ItemViewHolder extends RecyclerView.ViewHolder{

        private final TextView gameEdition;
        private final TextView gameIntro;
        private final TextView gameName;
        private final TextView gameSize;
        private final ImageView gamePic;
        private final RatingBar gameRatingBar;

        public ItemViewHolder(View itemView) {
            super(itemView);
            gameEdition = (TextView) itemView.findViewById(R.id.game_edition);
            gameIntro = (TextView) itemView.findViewById(R.id.game_intro);
            gameName = (TextView) itemView.findViewById(R.id.game_name);
            gameSize = (TextView) itemView.findViewById(R.id.game_size);
            gamePic = (ImageView) itemView.findViewById(R.id.game_pic);
            gameRatingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }
    }
}
