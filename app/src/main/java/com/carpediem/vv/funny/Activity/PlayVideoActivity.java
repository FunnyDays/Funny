package com.carpediem.vv.funny.Activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.carpediem.vv.funny.R;

import java.io.IOException;

import static com.carpediem.vv.funny.R.id.play;
import static com.carpediem.vv.funny.R.id.stop;

/**
 * Created by Administrator on 2016/8/31.
 */
public class PlayVideoActivity extends AppCompatActivity {


    private SurfaceView surfaceView;
    private Button buttonPlay;

    private Button buttonStop;
    private MediaPlayer mp;
    private ImageView imagePre;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play);
        mp=new MediaPlayer();
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        buttonPlay = (Button) findViewById(play);

        buttonStop = (Button) findViewById(stop);
        imagePre = (ImageView) findViewById(R.id.image_pre);
        initPlay();
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initData() {
        String VideoFileURL;
        try {
            Intent intent = getIntent();
            VideoFileURL = intent.getStringExtra("VideoFileURL");
            mp.setDataSource(VideoFileURL);
            mp.setDisplay(surfaceView.getHolder());
            mp.prepare();
            String propertyName = "Alpha";
            ObjectAnimator animator = ObjectAnimator.ofFloat(imagePre, propertyName, 1f, 0f);
            animator.setDuration(500);
            animator.start();

        }catch(IllegalArgumentException e) {
            e.printStackTrace();
        }catch(SecurityException e) {
            e.printStackTrace();
        }catch(IllegalStateException e) {
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void initPlay() {

        imagePre.setOnClickListener(new View.OnClickListener() {

            public String VideoFileURL;

            @Override
            public void onClick(View v) {
               // mp.reset();
                if(mp.isPlaying()){
                    mp.pause();
                    String propertyName = "Alpha";
                    ObjectAnimator animator = ObjectAnimator.ofFloat(imagePre, propertyName, 0f, 1f);
                    animator.setDuration(500);
                    animator.start();
                }else {
                    initData();
                        mp.start();
                        String propertyName = "Alpha";
                        ObjectAnimator animator = ObjectAnimator.ofFloat(imagePre, propertyName, 1f, 0f);
                        animator.setDuration(500);
                        animator.start();
                }


            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(mp.isPlaying()){
                    mp.pause();
                    ((Button)v).setText("继续");
                    String propertyName = "Alpha";
                    ObjectAnimator animator = ObjectAnimator.ofFloat(imagePre, propertyName, 0f, 1f);
                    animator.setDuration(500);
                    animator.start();
                }else{
                    mp.start();
                    ((Button)v).setText("暂停");
                    String propertyName = "Alpha";
                    ObjectAnimator animator = ObjectAnimator.ofFloat(imagePre, propertyName, 1f, 0f);
                    animator.setDuration(500);
                    animator.start();
                }

            }
        });

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(PlayVideoActivity.this, "视频播放完毕！", Toast.LENGTH_SHORT).show();
            }

        });


    }


    @Override
    protected void onDestroy() {
        if(mp.isPlaying()){
            mp.stop();
        }
        mp.release();
        super.onDestroy();
    }




}
