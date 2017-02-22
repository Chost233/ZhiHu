package com.pers.myc.zhihu.control;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;

import com.pers.myc.zhihu.listener.BitmapCallBackListener;
import com.pers.myc.zhihu.listener.HttpCallbackListener;
import com.pers.myc.zhihu.listener.ResponseListener;
import com.pers.myc.zhihu.model.gson.WelcomData;
import com.pers.myc.zhihu.untils.GsonUtil;
import com.pers.myc.zhihu.untils.HttpUtil;
import com.pers.myc.zhihu.view.HomeActivity;
import com.pers.myc.zhihu.view.WelcomeActivity;

import java.io.InputStream;

/**
 * 启动界面控制器
 */

public class WelcomControl {

    //首页界面接口
    private static final String HOME_URL = "http://news-at.zhihu.com/api/4/news/latest";
    //启动界面接口
    private static final String WELCOM_URL = "https://news-at.zhihu.com/api/7/prefetch-launch-images/480*728";

    //获取欢迎界面信息
    public void getWelcomData(final ResponseListener listener) {
        final WelcomData[] welcomData = {null};
        HttpUtil.sendHtttpRequest(WELCOM_URL, new HttpCallbackListener() {
            @Override
            public void onFinish(String response, InputStream inputStream) {
                //json解析
                welcomData[0] = (WelcomData) GsonUtil.analysis(response, WelcomData.class);
                HttpUtil.getHttpBitmap(welcomData[0].getCreatives().get(0).getUrl(), new BitmapCallBackListener() {
                    @Override
                    public void onFinish(final Bitmap bitmap) {
                        welcomData[0].getCreatives().get(0).setImage(bitmap);
                        listener.getData(welcomData[0]);
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
    }

    //首页信息
    private String mHomeResponse = "";
    //获取首页信息
    public void getHomeData(final ResponseListener listener) {
        HttpUtil.sendHtttpRequest(HOME_URL, new HttpCallbackListener() {
            @Override
            public void onFinish(String response, InputStream inputStream) {
                mHomeResponse = response;
                listener.getData(response);
            }
            @Override
            public void onError(Exception e) {
            }
        });
    }

    //启动首页
    public void startUpMainActivity(WelcomeActivity activity){
        //启动MainActivity
        Intent intent = new Intent(activity, HomeActivity.class);
        intent.putExtra("Response", mHomeResponse);
        intent.putExtra("URL", HOME_URL);
        activity.startActivity(intent);
    }

    //按键处理
    public boolean onKeyDown(int keyCode, KeyEvent event, WelcomeActivity activity) {
        //屏蔽返回键
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return activity.onKeyDown(keyCode, event);
    }
}
