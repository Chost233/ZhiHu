package com.pers.myc.zhihu.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pers.myc.zhihu.R;
import com.pers.myc.zhihu.control.WelcomControl;
import com.pers.myc.zhihu.listener.ResponseListener;
import com.pers.myc.zhihu.model.gson.WelcomData;

public class WelcomeActivity extends AppCompatActivity {
    //图片下图标
    private LinearLayout mText;

    //启动界面图片
    private ImageView mImageView;
    private TextView mTextView;

    //控制器
    WelcomControl mControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //初始化视图
        initView();

        //初始化控制器
        mControl = new WelcomControl();

        //配置启动界面
        final WelcomData[] welcomData = {null};
        mControl.getWelcomData(new ResponseListener() {
            @Override
            public void getData(Object obj) {
                welcomData[0] = (WelcomData) obj;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mImageView.setImageBitmap(welcomData[0].getCreatives().get(0).getImage());
                        mTextView.setText(welcomData[0].getCreatives().get(0).getText());
                    }
                });
            }
        });

        //设置图片透明度为0%
        mImageView.setAlpha(0.0f);

        //播放动画
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mImageView.setAlpha(1.0f);
                Animation alpha = new AlphaAnimation(0.0f, 1.0f);
                alpha.setDuration(500);
                mImageView.startAnimation(alpha);
            }
        };

        //网络请求最新消息
        mControl.getHomeData(new ResponseListener() {
            @Override
            public void getData(Object obj) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //启动界面动画
                            Thread.sleep(3000);
                            handler.sendEmptyMessage(0);
                            Thread.sleep(2000);
                            mControl.startUpMainActivity(WelcomeActivity.this);
                        } catch (InterruptedException e) {
                        }
                        finish();
                    }
                }).start();
            }
        });
        //底部图标动画
        Animation translate = new TranslateAnimation(0, 0, 600, 0);
        translate.setDuration(2000);
        mText.setAnimation(translate);
    }

    //初始化视图
    private void initView() {
        //初始化启动界面ImageView
        mImageView = (ImageView) findViewById(R.id.activity_welcome_image_view);
        //初始化启动界面TextView
        mTextView = (TextView) findViewById(R.id.activity_welcome_text_view);
        //初始化底部TextView
        mText = (LinearLayout) findViewById(R.id.activity_welcome_text);
    }

    //获取按键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mControl.onKeyDown(keyCode, event, WelcomeActivity.this);
    }
}
