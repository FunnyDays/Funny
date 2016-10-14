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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.carpediem.vv.funny.Base.BaseFragment;
import com.carpediem.vv.funny.Fragment.BooksFragment;
import com.carpediem.vv.funny.Fragment.DailyFragment;
import com.carpediem.vv.funny.Fragment.GameFragment;
import com.carpediem.vv.funny.Fragment.MusicFragment;
import com.carpediem.vv.funny.Fragment.VideoFragment;
import com.carpediem.vv.funny.R;
import com.carpediem.vv.funny.Utils.CacheUtils;
import com.carpediem.vv.funny.Utils.PermissionsChecker;
import com.carpediem.vv.funny.bean.Userbean.MyUser;

import java.util.ArrayList;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private SearchView.SearchAutoComplete editText;
    private static final int REQUEST_CODE = 0; // 请求码
    private PermissionsChecker mPermissionsChecker; // 权限检测器
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE

    };

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    private FragmentManager supportFragmentManager  = getSupportFragmentManager();
    private ArrayList<BaseFragment> fragments = new ArrayList<>();
    private FragmentTransaction fragmentTransaction;
    public static BottomNavigationBar bottomNavigationBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "c4e9104738e2747a6c63855e7d2a9b7d");
        setContentView(R.layout.activity_main);
        mPermissionsChecker = new PermissionsChecker(this);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        // App Logo
        // toolbar.setLogo(R.mipmap.ic_launcher);
        // Title
       // toolbar.setTitle("四叶草");
        // Sub Title
        //toolbar.setSubtitle("Sub title");
        //Navigation Icon
        //toolbar.setNavigationIcon(R.drawable.bottom_home_tab_bg);
       // toolbar.inflateMenu(R.menu.test);
       // setSupportActionBar(toolbar);
       /* toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.action_publish:
                        Toast.makeText(MainActivity.this, "正在开发" + itemId, Toast.LENGTH_SHORT).show();
                    case R.id.menu_refresh:
                        Toast.makeText(MainActivity.this, "正在开发" + itemId, Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });*/

        init();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        // Here, thisActivity is the current activity
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

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "每日").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "书籍").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "音乐").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "视频").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.drawable.ic_person_white_36dp, "设置").setActiveColorResource(R.color.colorPrimary))
                .setMode(BottomNavigationBar.MODE_FIXED)//设置底部代文字显示模式。MODE_DEFAULT默认MODE_FIXED代文字MODE_SHIFTING不带文字
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)//背景模式BACKGROUND_STYLE_RIPPLE涟漪BACKGROUND_STYLE_STATIC静态
                .initialise();
        setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            //当前的选中的tab
            @Override
            public void onTabSelected(int position) {
                Log.i("tab", "onTabSelected position:" + position);
               switch (position) {
                    case 0:
                        fragmentTransaction = supportFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_fragment,fragments.get(position), "daily_fragment").commit();
                       // toolbar.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        fragmentTransaction = supportFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_fragment,fragments.get(position), "main_fragment").commit();
                      //  toolbar.setVisibility(View.GONE);
                        break;
                    case 2:
                         fragmentTransaction = supportFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_fragment,fragments.get(position), "main_5fragment").commit();
                      //  toolbar.setVisibility(View.GONE);
                        break;
                    case 3:
                         fragmentTransaction = supportFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_fragment,fragments.get(position), "m2ain_fragment").commit();
                      //  toolbar.setVisibility(View.GONE);
                        break;
                    case 4:
                         fragmentTransaction = supportFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.main_fragment,fragments.get(position), "main_8fragment").commit();
                      //  toolbar.setVisibility(View.GONE);
                        break;

                }
                //下面代码会造成界面重叠的情况
              /*  if (fragments != null) {
                    if (position < fragments.size()) {
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        BaseFragment fragment = fragments.get(position);
                        if (fragment.isAdded()) {
                            ft.replace(R.id.main_fragment, fragment);
                        } else {
                            ft.add(R.id.main_fragment, fragment);
                        }
                        ft.commitAllowingStateLoss();
                    }
                }*/

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

            }
        });
    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.main_fragment, getFragments().get(0));
        transaction.commit();
    }
    private ArrayList<BaseFragment> getFragments() {

        fragments.add(DailyFragment.newInstance("Home"));
        fragments.add(BooksFragment.newInstance("Books"));
        fragments.add(MusicFragment.newInstance("Music"));
        fragments.add(VideoFragment.newInstance("Videos"));
        fragments.add(GameFragment.newInstance("Games"));
        return fragments;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.test, menu);
        final MenuItem item = menu.findItem(R.id.ab_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);


        //获取了SearchView，我们就能设置其相应的属性，一开始就处于显示SearchView的状态
       // mSearchView.setIconified(false);
        //不想让它隐藏SearchView
       // mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "提交查询", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(MainActivity.this, "数据变化", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        editText = (SearchView.SearchAutoComplete) mSearchView.findViewById(R.id.search_src_text);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_publish) {
            Toast.makeText(MainActivity.this, "投稿功能正在开发", Toast.LENGTH_SHORT).show();

        }

        if (id == R.id.menu_refresh) {
            Toast.makeText(MainActivity.this, "分享功能正在开发", Toast.LENGTH_SHORT).show();


        }
        return super.onOptionsItemSelected(item);
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

}
