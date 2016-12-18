package com.carpediem.vv.funny.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.bean.SearchBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/18.
 */
public class SearchAdapter extends RecyclerView.Adapter {
    private ArrayList<SearchBean> arrayList;
    private Activity mActivity;

    public SearchAdapter(Activity mActivity, ArrayList<SearchBean> arrayList) {
        this.arrayList = arrayList;
        this.mActivity = mActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_search_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder)holder).mTvHistory.setText(arrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvHistory;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTvHistory = (TextView) itemView.findViewById(R.id.tv_item_history);
        }
    }
}
