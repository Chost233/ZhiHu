package com.pers.myc.zhihu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Administrator on 2017/2/16.
 */

public class SingleNewsFragment extends Fragment {
    //网页展示
    public WebView mWebView;
    //URL
    private String mUrl;

    public SingleNewsFragment(String url) {
        mUrl = url;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_single_news, container, false);

        //初始化视图
        initview(v);

        //配置WebView
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (url.toString().indexOf("http://daily.zhihu.com/story/") != -1) {
                    Log.e("url:", url + "");
                    String javascript = "javascript:function hideOther() {" +
                            "document.getElementsByClassName('global-header')[0].style.display='none';" +
                            "document.getElementsByClassName('header-for-mobile')[0].style.display='none';" +
                            "document.getElementsByClassName('qr')[0].style.display='none';" +
                            "document.getElementsByClassName('question')[1].style.display='none';" +
                            "document.getElementsByClassName('bottom-recommend-download-link')[0].style.display='none';" +
                            "document.getElementsByClassName('bottom-wrap')[0].style.display='none';}";

                    //创建方法
                    view.loadUrl(javascript);
                    //加载方法
                    view.loadUrl("javascript:hideOther();");
                }
            }
        });
        mWebView.loadUrl(mUrl);

        return v;
    }

    private void initview(View v) {
        mWebView = (WebView) v.findViewById(R.id.fragment_single_news_web_view);
    }

}
