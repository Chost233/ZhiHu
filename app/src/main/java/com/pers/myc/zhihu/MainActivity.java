package com.pers.myc.zhihu;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //标题栏
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    //左侧滑栏
    private DrawerLayout mDrawerLayout;
    //左侧滑栏布局
    private NavigationView mNavigationView;
    //Fragment管理器
    private FragmentManager mFragmentManager;
    //展示Fragment
    private List<Fragment> mContentList;
    //正在展示的Fragment
    private Fragment mDisplayFragment;
    //Fragment展示布局
    private FrameLayout mFrameLayout;
    //主页json信息
    private String mResponse;
    //URL
    private String mURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化视图
        initview();
        //初始化数据
        initData();
        //初始化屏幕宽高
        configScreendata();
        //测试
        test();

        //设置显示左上角图标
        mNavigationView.setCheckedItem(R.id.nav_home);
        //设置导航页图标无默认色
        mNavigationView.setItemIconTintList(null);
        //监听导航页图标选择事件
        mNavigationView.setNavigationItemSelectedListener(this);
        //设置toolbar为actionbar样式
        setSupportActionBar(mToolbar);
        //显示actionbar左边按钮
        mActionBar = getSupportActionBar();
        mActionBar.setTitle("今日热闻");
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }
        //添加Fragment到列表
        mContentList.add(0, new NewsFragment(mResponse, NewsFragment.LATEST_NEWS, "http://news-at.zhihu.com/api/4/news/latest"));
        //获取fragment管理器
        mFragmentManager = getSupportFragmentManager();
        //注册显示主页fragment
        mFragmentManager.beginTransaction().add(R.id.activity_main_news_list, mContentList.get(0)).commit();
        mDisplayFragment = mContentList.get(0);
    }

    //测试
    private void test() {

    }


    //配置设置列表
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listitem_toolbar, menu);
        return true;
    }

    //监听标题栏按钮点击事件
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.notice:
                break;
            //切换夜间模式
            case R.id.night_mode:
                if (!Config.isNightMode()) {
                    Config.setNightMode(true);
                    mToolbar.setBackgroundColor(Color.parseColor("#222222"));
                    mFrameLayout.setBackgroundColor(Color.parseColor("#343434"));
                    Resources resources = (Resources)getBaseContext().getResources();
                    ColorStateList csl = (ColorStateList)resources.getColorStateList(R.color.item_color_nightmode, null);
                    mNavigationView.setItemTextColor(csl);
                    mNavigationView.setBackgroundColor(Color.parseColor("#343434"));
                    View v = mNavigationView.getHeaderView(0);
                    v.findViewById(R.id.nav_header_layout).setBackgroundColor(Color.parseColor("#252525"));

                } else {
                    Config.setNightMode(false);
                    mToolbar.setBackgroundColor(Color.parseColor("#00a1ec"));
                    mFrameLayout.setBackgroundColor(Color.parseColor("#f2f2f2"));
                    Resources resources = (Resources)getBaseContext().getResources();
                    ColorStateList csl = (ColorStateList)resources.getColorStateList(R.color.item_coloe_normal, null);
                    mNavigationView.setItemTextColor(csl);
                    mNavigationView.setBackgroundColor(Color.parseColor("#f9f9f9"));
                    View v = mNavigationView.getHeaderView(0);
                    v.findViewById(R.id.nav_header_layout).setBackgroundColor(Color.parseColor("#01a0eb"));
                }
                break;
        }
        return true;
    }

    //初始化视图
    public void initview() {
        mToolbar = (Toolbar) findViewById(R.id.activity_main_tool_bar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.activity_main_navigation_view);
        mFrameLayout = (FrameLayout) findViewById(R.id.activity_main_news_list);
    }

    //初始化数据
    private void initData() {
        mContentList = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            mContentList.add(null);
        }
        mResponse = (String) getIntent().getExtras().get("Response");
        mURL = (String) getIntent().getExtras().get("URL");
    }

    //切换framgent
    private void switchFragment(final String url, final int position) {
        if (mContentList.get(position) == null) {
            HttpUtil.sendHtttpRequest(url, new HttpCallbackListener() {
                @Override
                public void onFinish(String response, InputStream inputStream) {
                    Fragment fragment = new NewsFragment(response, NewsFragment.THEME_NEWS, url);
                    mContentList.add(position, fragment);
                    mFragmentManager.beginTransaction().hide(mDisplayFragment).commit();
                    mFragmentManager.beginTransaction().add(R.id.activity_main_news_list, mContentList.get(position)).commit();
                    mDisplayFragment = mContentList.get(position);
                }

                @Override
                public void onError(Exception e) {
                }
            });
        } else {
            mFragmentManager.beginTransaction().hide(mDisplayFragment).commit();
            mFragmentManager.beginTransaction().show(mContentList.get(position)).commit();
            mDisplayFragment = mContentList.get(position);
        }
    }

    //获取屏幕宽高
    public void configScreendata() {
        WindowManager manager = getWindowManager();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        Config.setScreenHeight(displayMetrics.heightPixels);
        Config.setScreenWidth(displayMetrics.widthPixels);
    }

    //导航页选项监听
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                switchFragment("", 0);
                mActionBar.setTitle(Config.getThemeTitle()[0]);
                break;
            case R.id.nav_item_1:
                switchFragment("http://news-at.zhihu.com/api/4/theme/3", 1);
                mActionBar.setTitle(Config.getThemeTitle()[1]);
                break;
            case R.id.nav_item_2:
                switchFragment("http://news-at.zhihu.com/api/4/theme/10", 2);
                mActionBar.setTitle(Config.getThemeTitle()[2]);
                break;
            case R.id.nav_item_3:
                switchFragment("http://news-at.zhihu.com/api/4/theme/2", 3);
                mActionBar.setTitle(Config.getThemeTitle()[3]);
                break;
            case R.id.nav_item_4:
                switchFragment("http://news-at.zhihu.com/api/4/theme/7", 4);
                mActionBar.setTitle(Config.getThemeTitle()[4]);
                break;
            case R.id.nav_item_5:
                switchFragment("http://news-at.zhihu.com/api/4/theme/9", 5);
                mActionBar.setTitle(Config.getThemeTitle()[5]);
                break;
            case R.id.nav_item_6:
                switchFragment("http://news-at.zhihu.com/api/4/theme/13", 6);
                mActionBar.setTitle(Config.getThemeTitle()[6]);
                break;
            case R.id.nav_item_7:
                switchFragment("http://news-at.zhihu.com/api/4/theme/12", 7);
                mActionBar.setTitle(Config.getThemeTitle()[7]);
                break;
            case R.id.nav_item_8:
                switchFragment("http://news-at.zhihu.com/api/4/theme/11", 8);
                mActionBar.setTitle(Config.getThemeTitle()[8]);
                break;
            case R.id.nav_item_9:
                switchFragment("http://news-at.zhihu.com/api/4/theme/4", 9);
                mActionBar.setTitle(Config.getThemeTitle()[9]);
                break;
            case R.id.nav_item_10:
                switchFragment("http://news-at.zhihu.com/api/4/theme/5", 10);
                mActionBar.setTitle(Config.getThemeTitle()[10]);
                break;
            case R.id.nav_item_11:
                switchFragment("http://news-at.zhihu.com/api/4/theme/6", 11);
                mActionBar.setTitle(Config.getThemeTitle()[11]);
                break;
            case R.id.nav_item_12:
                switchFragment("http://news-at.zhihu.com/api/4/theme/8", 12);
                mActionBar.setTitle(Config.getThemeTitle()[12]);
                break;
        }
        return true;
    }
}