package com.carpediem.vv.funny.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.carpediem.vv.funny.Adapter.MainContentVPAdapter;
import com.carpediem.vv.funny.Base.BaseFragment;
import com.carpediem.vv.funny.Fragment.AllTabFragment;
import com.carpediem.vv.funny.Fragment.BooksFragment;
import com.carpediem.vv.funny.Fragment.DailyFragment;
import com.carpediem.vv.funny.Fragment.GameFragment;
import com.carpediem.vv.funny.Fragment.LYMoviesFragment;
import com.carpediem.vv.funny.Fragment.MusicFragment;
import com.carpediem.vv.funny.Fragment.UserFragment;
import com.carpediem.vv.funny.Fragment.VideoFragment;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.Utils.CacheUtils;
import com.carpediem.vv.funny.Utils.PermissionsChecker;
import com.carpediem.vv.funny.bean.Userbean.MyUser;
import com.carpediem.vv.funny.weight.CustomViewPager;

import java.util.ArrayList;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0; // 请求码
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE

    };

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private FragmentManager supportFragmentManager  = getSupportFragmentManager();
    private ArrayList<BaseFragment> fragments;
    public static BottomNavigationBar bottomNavigationBar;
    private CustomViewPager customViewPager;
    private MainContentVPAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //防止布局被软键盘扰乱
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //阻止截屏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        Bmob.initialize(this, "c4e9104738e2747a6c63855e7d2a9b7d");
        setContentView(R.layout.activity_main);
        mPermissionsChecker = new PermissionsChecker(this);
        init();

    }

    private void initUser() {
        Boolean login = CacheUtils.getBoolean(this, "login", false);
        if(login){

        }else {
            TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
            String szImei = TelephonyMgr.getDeviceId();
            final MyUser bu = new MyUser();
            bu.setUsername(szImei);
            bu.setPassword("123456");
            bu.setModel(android.os.Build.MODEL);
            //注意：不能用save方法进行注册
            bu.signUp(new SaveListener<MyUser>() {
                @Override
                public void done(MyUser s, BmobException e) {
                    if (e == null) {
                        Log.i("bmob", "注册成功"+bu.getObjectId());
                        CacheUtils.putBoolean(MainActivity.this,"login",true);
                    } else {
                        Log.i("bmob", "注册失败");
                    }
                }
            });
            Log.i("bmob", "注册12121成功"+bu.getObjectId());
            BmobUser.loginByAccount(szImei, "123456", new LogInListener<MyUser>() {
                @Override
                public void done(MyUser user, BmobException e) {
                    if(user!=null){
                        Log.i("bmob","用户登陆成功");
                    }
                }
            });
        }
    }

    private void init() {
        getFragments();
        customViewPager = (CustomViewPager) findViewById(R.id.main_fragment);
        customViewPager.setAdapter(adapter);
        customViewPager.setOffscreenPageLimit(4);//设置缓存页数，缓存所有fragment
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "每日").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "书籍").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "音乐").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "电影").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_person_white_36dp, "设置").setActiveColorResource(R.color.colorPrimary))
                .setMode(BottomNavigationBar.MODE_FIXED)//设置底部代文字显示模式。MODE_DEFAULT默认MODE_FIXED代文字MODE_SHIFTING不带文字
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)//背景模式BACKGROUND_STYLE_RIPPLE涟漪BACKGROUND_STYLE_STATIC静态
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            //当前的选中的tab
            @Override
            public void onTabSelected(int position) {
               switch (position) {
                    case 0:
                        customViewPager.setCurrentItem(0);
                        break;
                    case 1:
                        customViewPager.setCurrentItem(1);
                        break;
                    case 2:
                        customViewPager.setCurrentItem(2);
                        break;
                    case 3:
                        customViewPager.setCurrentItem(3);
                            break;
                    case 4:
                        customViewPager.setCurrentItem(4);
                        break;

                }

            }

            //上一个选中的tab
            @Override
            public void onTabUnselected(int position) {
                Log.i("tab", "onTabUnselected position:" + position);

            }

            //当前tab被重新选中
            @Override
            public void onTabReselected(int position) {
                Log.i("tab", "onTabReselected position:" + position);
                fragments.get(position).initData();
            }
        });
    }


    private ArrayList<BaseFragment> getFragments() {
        fragments = new ArrayList<>();
        fragments.add(AllTabFragment.newInstance("首页"));
        fragments.add( BooksFragment.newInstance("书籍"));
        fragments.add( MusicFragment.newInstance("音乐"));
        fragments.add( new LYMoviesFragment());
        fragments.add( UserFragment.newInstance());
        adapter = new MainContentVPAdapter(supportFragmentManager, fragments);
        return fragments;
    }



    @Override protected void onResume() {
        super.onResume();
        // 缺少权限时, 进入权限配置页面
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            //进入到这里代表没有权限.
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_PHONE_STATE)){
                //已经禁止提示了
                Toast.makeText(MainActivity.this, "您已禁止该权限，需要重新开启。", Toast.LENGTH_SHORT).show();
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);

            }
        } else {
            initUser();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if(grantResults.length >0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //用户同意授权
                    initUser();
                }else{
                    //用户拒绝授权
                }
                break;
        }
    }
    private long firstTime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("wei", ":MainActivity:OnKey事件");
        if (fragments.get(0) instanceof AllTabFragment){
            AllTabFragment.onKeyDown(keyCode,event);
        }
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-firstTime>2000){
                Toast.makeText(MainActivity.this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
                firstTime=System.currentTimeMillis();
            }else{
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
