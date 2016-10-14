package com.carpediem.vv.funny.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.carpediem.vv.funny.R;

/**
 * Created by Administrator on 2016/9/8.
 */
public class BookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView textView = (TextView) findViewById(R.id.text_book);
        TextView bookName = (TextView) findViewById(R.id.book_name);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_download);

        Intent intent = getIntent();
        String text_book = intent.getStringExtra("text_book");
        String image_book = intent.getStringExtra("image_book");
        String name_book = intent.getStringExtra("name_book");
        Glide.with(this)
                .load(image_book)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
        bookName.setText(name_book);
        textView.setText(text_book);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        assert floatingActionButton != null;
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BookDetailActivity.this,"1暂不提供下载",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
