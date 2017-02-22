package com.pers.myc.zhihu.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.pers.myc.zhihu.R;
import com.pers.myc.zhihu.control.HomeControl;
import com.pers.myc.zhihu.fragment.NewsFragment;

public class HomeActivity extends AppCompatActivity {

    //标题栏
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    //左侧滑栏
    private DrawerLayout mDrawerLayout;
    //左侧滑栏布局
    private NavigationView mNavigationView;
    //Fragment展示布局
    private FrameLayout mFrameLayout;
    //控制器
    private HomeControl mControl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化视图
        initview();
        //设置toolbar为actionbar样式
        setSupportActionBar(mToolbar);
        //初始化ActionBar
        mActionBar = getSupportActionBar();
        //初始化控制器
        mControl = new HomeControl(HomeActivity.this, this.getSupportFragmentManager(), mActionBar);
        //初始化数据
        mControl.initData();
        //设置显示左上角图标
        mNavigationView.setCheckedItem(R.id.nav_home);
        //设置导航页图标无默认色
        mNavigationView.setItemIconTintList(null);
        //监听导航页图标选择事件
        mNavigationView.setNavigationItemSelectedListener(mControl);
        //显示actionbar左边按钮
        mActionBar.setTitle("今日热闻");
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }
        //添加Fragment到列表
        mControl.getContentList().add(0, new NewsFragment(mControl.getResponse(), NewsFragment.LATEST_NEWS, "http://news-at.zhihu.com/api/4/news/latest"));
        //注册显示主页fragment
        mControl.getFragmentManager().beginTransaction().add(R.id.activity_main_news_list, mControl.getContentList().get(0)).commit();
        mControl.setDisplayFragment(mControl.getContentList().get(0));
    }

    //配置设置列表
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listitem_toolbar, menu);
        return true;
    }

    //获取标题栏按钮点击事件
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mControl.onOptionsItemSelected(item, mDrawerLayout, mToolbar, mFrameLayout, mNavigationView);
        return true;
    }

    //初始化视图
    public void initview() {
        mToolbar = (Toolbar) findViewById(R.id.activity_main_tool_bar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.activity_main_navigation_view);
        mFrameLayout = (FrameLayout) findViewById(R.id.activity_main_news_list);
    }
}