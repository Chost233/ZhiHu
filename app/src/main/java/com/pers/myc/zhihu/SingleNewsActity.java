package com.pers.myc.zhihu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class SingleNewsActity extends AppCompatActivity {

    //标题栏
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    //Fragment管理器
    private FragmentManager mFragmentManager;
    //新闻Fragment
    private List<SingleNewsFragment> mNewFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_news);

        //初始化视图
        initView();
        //初始化数据
        initData();
        //注册显示Fragment
        mFragmentManager.beginTransaction().add(R.id.activity_single_news_frame_layout, mNewFragments.get(0)).commit();
        //标题颜色
        if (Config.isNightMode()) {
            mToolbar.setBackgroundColor(Color.parseColor("#222222"));
        } else {
            mToolbar.setBackgroundColor(Color.parseColor("#00a1ec"));
        }
        //显示标题栏左边图标
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initData() {
        //解析response
        String response = (String) this.getIntent().getSerializableExtra("response");
        Gson gson = new Gson();
        SingleNews singleNews = gson.fromJson(response, SingleNews.class);
        //初始化Frgment列表
        mNewFragments = new ArrayList<SingleNewsFragment>();
        mNewFragments.add(0, new SingleNewsFragment(singleNews.getShare_url()));
        //初始化Fragment管理器
        mFragmentManager = getSupportFragmentManager();
    }

    //监听标题栏按钮点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }

    //初始化视图
    private void initView() {
        //获取ToolBar
        mToolbar = (Toolbar) findViewById(R.id.activity_single_news_tool_bar);
        //设置toolbar为actionbar样式
        setSupportActionBar(mToolbar);
        //获取ActoinBar
        mActionBar = getSupportActionBar();
    }
}
