package com.pers.myc.zhihu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.InputStream;

public class WelcomeActivity extends AppCompatActivity {
    private final Context THIS = this;
    private LinearLayout mText;
    private String mResponse;
    private static final String URL = "http://news-at.zhihu.com/api/4/news/latest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //初始化ImageView
        final ImageView mImageView = (ImageView) findViewById(R.id.activity_welcome_image_view);
        //初始化TextView
        final TextView mTextView = (TextView)findViewById(R.id.activity_welcome_text_view);
        //网络请求获取启动界面
        HttpUtil.sendHtttpRequest("https://news-at.zhihu.com/api/7/prefetch-launch-images/480*728", new HttpCallbackListener() {
            @Override
            public void onFinish(String response, InputStream inputStream) {
                //json解析
                Gson gson = new Gson();
                final HomeImage homeImage = gson.fromJson(response, HomeImage.class);
                HttpUtil.getHttpBitmap(homeImage.getCreatives().get(0).getUrl(), new BitmapCallBackListener() {
                    @Override
                    public void onFinish(final Bitmap bitmap) {
                        //在UI线程修改图片
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (bitmap != null) {
                                    mImageView.setImageBitmap(bitmap);
                                    mTextView.setText(homeImage.getCreatives().get(0).getText());
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }

            @Override
            public void onError(Exception e) {

            }
        });

        //设置图片透明度为0%
        mImageView.setAlpha(0.0f);
        //初始化底部TextView
        mText = (LinearLayout) findViewById(R.id.activity_welcome_text);
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
        HttpUtil.sendHtttpRequest(URL, new HttpCallbackListener() {
            @Override
            public void onFinish(String response, InputStream inputStream) {
                Gson gson = new Gson();
                final LatestNews latestNews = gson.fromJson(response, LatestNews.class);
                mResponse = response;
                Log.e("date:", latestNews.getDate());
                Log.e("stories:", latestNews.getStories().size() + "");
                Log.e("top_stories:", latestNews.getTop_stories().get(0).getTitle());
                Log.e("imageURL:", latestNews.getStories().get(0).getImages().get(0));
                Log.e("numbers:",latestNews.getTop_stories().size()+"");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            handler.sendEmptyMessage(0);
                            Thread.sleep(2000);
                            Intent intent = new Intent(THIS, MainActivity.class);
                            intent.putExtra("Response", mResponse);
                            intent.putExtra("URL", URL);
                            startActivity(intent);
                        } catch (InterruptedException e) {
                        }
                        finish();
                    }
                }).start();
            }

            @Override
            public void onError(Exception e) {

            }
        });

        Animation translate = new TranslateAnimation(0, 0, 600, 0);
        translate.setDuration(2000);
        mText.setAnimation(translate);
    }

    //屏蔽BACK键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
