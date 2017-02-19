package com.pers.myc.zhihu;

import android.graphics.Bitmap;

/**
 * 图片回调接口
 */
//网络请求图片回调接口
public interface BitmapCallBackListener {
    void onFinish(Bitmap bitmap);

    void onError(Exception e);
}
