package com.carpediem.vv.funny.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.carpediem.vv.funny.Activity.BookDetailActivity;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.bean.BookBean.Book;
import com.carpediem.vv.funny.bean.BookBean.BookTopic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class BookAdapter extends RecyclerView.Adapter {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private Activity mActivity;
    private List<BookTopic> mDatas;
    private ArrayList<Book> bookList;

    public BookAdapter(Context mActivity, List<BookTopic> data, ArrayList<Book> bookList) {
        this.mActivity = (Activity) mActivity;
        this.mDatas = data;
        this.bookList = bookList;

    }

    public void setData(List<BookTopic> data) {
        this.mDatas = data;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     /*     if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_with_data, parent,
                    false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_foot, parent,
                    false);
            return new FootViewHolder(view);
        }
        return null;*/
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_books, parent, false);
        return new ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ArrayList<Book> list = new ArrayList<Book>();
        String textContent = mDatas.get(position).getTopicName();

        if (!textContent.isEmpty()) {
            ((ItemViewHolder) holder).textCategory.setText(textContent);
        }
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getBookLabel().equals(mDatas.get(position).getTopicName())) {
                list.add(bookList.get(i));
            }
        }
        if (list.size() > 0) {
            ((ItemViewHolder) holder).textOneName.setText(list.get(0).getBookName());
            ((ItemViewHolder) holder).textTwoName.setText(list.get(1).getBookName());
            ((ItemViewHolder) holder).textThreeName.setText(list.get(2).getBookName());
            Glide.with(mActivity)
                    .load(list.get(0).getBookImage())
                    .fitCenter()
                    .placeholder(R.drawable.button_bg_press)
                    .error(R.drawable.imagenotfound)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(((ItemViewHolder) holder).imageViewOne);
            Glide.with(mActivity)
                    .load(list.get(1).getBookImage())
                    .fitCenter()
                    .placeholder(R.drawable.button_bg_press)
                    .error(R.drawable.imagenotfound)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(((ItemViewHolder) holder).imageViewTwo);
            Glide.with(mActivity)
                    .load(list.get(2).getBookImage())
                    .fitCenter()
                    .placeholder(R.drawable.button_bg_press)
                    .error(R.drawable.imagenotfound)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(((ItemViewHolder) holder).imageViewThree);
        }
        ((ItemViewHolder) holder).imageViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent=new Intent(mActivity,BookDetailActivity.class);
                mIntent.putExtra("text_book",list.get(0).getBookInfo());
                mIntent.putExtra("image_book",list.get(0).getBookImage());
                mIntent.putExtra("name_book",list.get(0).getBookName());
                mActivity.startActivity(mIntent);
            }
        });
        ((ItemViewHolder) holder).imageViewTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(mActivity,BookDetailActivity.class);
                mIntent.putExtra("text_book",list.get(1).getBookInfo());
                mIntent.putExtra("image_book",list.get(1).getBookImage());
                mIntent.putExtra("name_book",list.get(1).getBookName());

                mActivity.startActivity(mIntent);
            }
        });
        ((ItemViewHolder) holder).imageViewThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(mActivity,BookDetailActivity.class);
                mIntent.putExtra("text_book",list.get(2).getBookInfo());
                mIntent.putExtra("image_book",list.get(2).getBookImage());
                mIntent.putExtra("name_book",list.get(2).getBookName());

                mActivity.startActivity(mIntent);
            }
        });

    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView textCategory;
        private final ImageView imageViewOne;
        private final ImageView imageViewTwo;
        private final ImageView imageViewThree;
        private final TextView textOneName;
        private final TextView textTwoName;
        private final TextView textThreeName;

        public ItemViewHolder(View view) {
            super(view);
            textCategory = (TextView) view.findViewById(R.id.text_category);
            imageViewOne = (ImageView) view.findViewById(R.id.book_one);
            imageViewTwo = (ImageView) view.findViewById(R.id.book_two);
            imageViewThree = (ImageView) view.findViewById(R.id.book_three);
            textOneName = (TextView) view.findViewById(R.id.book_one_name);
            textTwoName = (TextView) view.findViewById(R.id.book_two_name);
            textThreeName = (TextView) view.findViewById(R.id.book_three_name);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }
}
