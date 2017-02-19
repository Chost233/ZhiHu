package com.pers.myc.zhihu;

import java.io.InputStream;

/**
 * 网络请求回调接口
 */

//网络请求回调接口
public interface HttpCallbackListener {
    void onFinish(String response, InputStream inputStream);

    void onError(Exception e);
}
