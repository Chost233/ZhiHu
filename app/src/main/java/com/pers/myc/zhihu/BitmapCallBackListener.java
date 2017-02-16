package com.pers.myc.zhihu;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/1/21.
 */
//网络请求图片回调接口
public interface BitmapCallBackListener {
    void onFinish(Bitmap bitmap);

    void onError(Exception e);
}
