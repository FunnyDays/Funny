package com.carpediem.vv.funny.Fragment;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.carpediem.vv.funny.Base.BasePager;
import com.carpediem.vv.funny.R;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Administrator on 2016/6/28.
 */
public class TestFragment extends BasePager  {


    public SwipeRefreshLayout swiperefresh;
    private ListView listView;
    private ArrayList<String> arrayList;

    public TestFragment(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_test, null);
        /**
         * volley的ImageLoader暂时不支持GIF,需要自定义，缓存策略也不完善
         */
       /* final GifImageView gif = (GifImageView) view.findViewById(R.id.gif);
        //创建一个请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
        //创建imageLoader
        ImageLoader imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        //获取一个ImageListener对象
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(gif,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher);

        imageLoader.get("http://bmob-cdn-5372.b0.upaiyun.com/2016/08/06/bb7c7a1f40a47ef1801acf623f266f16.gif", listener);
*/
        /**
         * Glide图片加载
         */
        String imageUrl="http://78re52.com1.z0.glb.clouddn.com/resource/gogopher.jpg?imageView2/1/w/200/h/200/format/jpg";
        String gifUrl="http://bmob-cdn-5372.b0.upaiyun.com/2016/08/06/bb7c7a1f40a47ef1801acf623f266f16.gif";
        GifImageView gif = (GifImageView) view.findViewById(R.id.gif);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        //Glide.with(mActivity).load(imageUrl).into(imageView);
        Glide.with(mActivity).load(gifUrl).asGif().into(imageView);
        Glide.with(mActivity).load(gifUrl).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);

        // Glide.with(mActivity).load(imageUrl).placeholder(R.mipmap.ic_launcher).error(R.mipmap.erroriamge).into(imageView);
/*
        //final GifImageView gif = (GifImageView) view.findViewById(R.id.gif);
        final MediaController mc = new MediaController(mActivity);
        //将图片放入媒体控制器中
        mc.setMediaPlayer((GifDrawable) gif.getDrawable());
        ((GifDrawable) gif.getDrawable()).start();
        mc.setAnchorView(gif);
        //给图片添加监听，点击就会显示播放控件
        gif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((GifDrawable) gif.getDrawable()).isPlaying()) {
                    ((GifDrawable) gif.getDrawable()).stop();
                }else {
                    ((GifDrawable) gif.getDrawable()).reset();
                }
            }
        });
*/

        return view;
    }




}
