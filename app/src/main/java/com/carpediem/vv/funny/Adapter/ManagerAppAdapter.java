package com.carpediem.vv.funny.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carpediem.vv.funny.R;

import java.util.List;

/**
 * Created by Administrator on 2016/12/22.
 */
public class ManagerAppAdapter extends RecyclerView.Adapter {
    private Activity mActivity;
    private List mList;

    public ManagerAppAdapter(Activity activity, List list) {
        mActivity = activity;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_manager_app_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder){
            ((ItemViewHolder)holder).mTextView.setText(mList.get(position).toString());
        }
    }

    @Override
    public int getItemCount() {
        return mList==null? 0: mList.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTextView;
        public ItemViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.app_name);
        }
    }
}
