package com.pers.myc.zhihu.control;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.pers.myc.zhihu.Config;
import com.pers.myc.zhihu.R;
import com.pers.myc.zhihu.fragment.NewsFragment;
import com.pers.myc.zhihu.listener.HttpCallbackListener;
import com.pers.myc.zhihu.untils.HttpUtil;
import com.pers.myc.zhihu.view.HomeActivity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页控制器
 */

public class HomeControl implements NavigationView.OnNavigationItemSelectedListener {

    //HomgActivity
    private HomeActivity mActivity;
    //fragment控制器
    private FragmentManager mFragmentManager;
    //标题栏
    private ActionBar mActionBar;
    //展示Fragment
    private List<Fragment> mContentList;
    //主页json信息
    private String mResponse;
    //URL
    private String mURL;
    //正在展示的Fragment
    private Fragment mDisplayFragment;

    public FragmentManager getFragmentManager() {
        return mFragmentManager;
    }
    public List<Fragment> getContentList() {
        return mContentList;
    }
    public void setContentList(List<Fragment> contentList) {
        mContentList = contentList;
    }
    public String getResponse() {
        return mResponse;
    }
    public void setResponse(String response) {
        mResponse = response;
    }
    public Fragment getDisplayFragment() {
        return mDisplayFragment;
    }
    public void setDisplayFragment(Fragment displayFragment) {
        mDisplayFragment = displayFragment;
    }

    public HomeControl(HomeActivity homeActivity, FragmentManager fragmentManager, ActionBar actionBar) {
        this.mFragmentManager = fragmentManager;
        this.mActionBar = actionBar;
        this.mActivity = homeActivity;
    }

    //初始化数据
    public void initData() {
        mContentList = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            mContentList.add(null);
        }
        mResponse = (String) mActivity.getIntent().getExtras().get("Response");
        mURL = (String) mActivity.getIntent().getExtras().get("URL");
    }

    //处理按钮点击事件
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onOptionsItemSelected(MenuItem item, DrawerLayout drawerLayout, Toolbar toolbar, FrameLayout frameLayout, NavigationView navigationView) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.notice:
                break;
            //切换夜间模式
            case R.id.night_mode:
                if (!Config.isNightMode()) {
                    Config.setNightMode(true);
                    toolbar.setBackgroundColor(Color.parseColor("#222222"));
                    frameLayout.setBackgroundColor(Color.parseColor("#343434"));
                    Resources resources = mActivity.getBaseContext().getResources();
                    ColorStateList csl = resources.getColorStateList(R.color.item_color_nightmode, null);
                    navigationView.setItemTextColor(csl);
                    navigationView.setBackgroundColor(Color.parseColor("#343434"));
                    View v = navigationView.getHeaderView(0);
                    v.findViewById(R.id.nav_header_layout).setBackgroundColor(Color.parseColor("#252525"));

                } else {
                    Config.setNightMode(false);
                    toolbar.setBackgroundColor(Color.parseColor("#00a1ec"));
                    frameLayout.setBackgroundColor(Color.parseColor("#f2f2f2"));
                    Resources resources = mActivity.getBaseContext().getResources();
                    ColorStateList csl = resources.getColorStateList(R.color.item_coloe_normal, null);
                    navigationView.setItemTextColor(csl);
                    navigationView.setBackgroundColor(Color.parseColor("#f9f9f9"));
                    View v = navigationView.getHeaderView(0);
                    v.findViewById(R.id.nav_header_layout).setBackgroundColor(Color.parseColor("#01a0eb"));
                }
                break;
        }
    }

    //导航页选项监听处理
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
}
