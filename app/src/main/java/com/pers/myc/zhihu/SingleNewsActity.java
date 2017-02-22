package com.pers.myc.zhihu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.InputStream;
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
    //点赞数
    private TextView mTextViewLike;
    //评论数
    private TextView mTextViewComment;

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
        final Gson gson = new Gson();
        SingleNews singleNews = gson.fromJson(response, SingleNews.class);
        //设置评论数和点赞数
        HttpUtil.sendHtttpRequest("http://news-at.zhihu.com/api/4/story-extra/" + singleNews.getId(), new HttpCallbackListener() {
            @Override
            public void onFinish(String response, InputStream inputStream) {
                final NewsExtra newsExtra = gson.fromJson(response, NewsExtra.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextViewLike.setText(newsExtra.getPopularity());
                        mTextViewComment.setText(newsExtra.getComments());
                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }
        });
        //初始化Frgment列表
        mNewFragments = new ArrayList<>();
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
        //点赞数
        mTextViewLike = (TextView) findViewById(R.id.activity_single_news_text_view_like);
        //评论数
        mTextViewComment = (TextView) findViewById(R.id.activity_single_news_text_view_comment);
    }
}
