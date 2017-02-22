package com.pers.myc.zhihu.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.pers.myc.zhihu.Config;
import com.pers.myc.zhihu.R;
import com.pers.myc.zhihu.control.SingleNewsControl;
import com.pers.myc.zhihu.listener.ResponseListener;
import com.pers.myc.zhihu.model.gson.NewsExtra;

public class SingleNewsActity extends AppCompatActivity {

    //标题栏
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    //点赞数
    private TextView mTextViewLike;
    //评论数
    private TextView mTextViewComment;
    //控制器
    private SingleNewsControl mControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_news);

        //初始化视图
        initView();
        //初始化控制器
        mControl = new SingleNewsControl(SingleNewsActity.this, getSupportFragmentManager());
        //初始化数据
        mControl.initData(new ResponseListener() {
            @Override
            public void getData(Object obj) {
                final NewsExtra newsExtra = (NewsExtra)obj;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextViewLike.setText(newsExtra.getPopularity());
                        mTextViewComment.setText(newsExtra.getComments());
                    }
                });
            }
        });
        //注册显示Fragment
        mControl.getFragmentManager().beginTransaction().add(R.id.activity_single_news_frame_layout, mControl.getNewFragments().get(0)).commit();
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

    //获取标题栏按钮点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mControl.onOptionsItemSelected(item);
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
