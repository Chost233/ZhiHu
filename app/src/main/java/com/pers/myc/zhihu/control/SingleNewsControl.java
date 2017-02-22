package com.pers.myc.zhihu.control;

import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.pers.myc.zhihu.fragment.SingleNewsFragment;
import com.pers.myc.zhihu.listener.HttpCallbackListener;
import com.pers.myc.zhihu.listener.ResponseListener;
import com.pers.myc.zhihu.model.gson.NewsExtra;
import com.pers.myc.zhihu.model.gson.SingleNews;
import com.pers.myc.zhihu.untils.GsonUtil;
import com.pers.myc.zhihu.untils.HttpUtil;
import com.pers.myc.zhihu.view.SingleNewsActity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 新闻信息界面控制器
 */

public class SingleNewsControl {

    //Fragment管理器
    private FragmentManager mFragmentManager;
    //新闻Fragment
    private List<SingleNewsFragment> mNewFragments;
    //SingleNewsActivity
    private SingleNewsActity mActity;

    public List<SingleNewsFragment> getNewFragments() {
        return mNewFragments;
    }
    public FragmentManager getFragmentManager() {
        return mFragmentManager;
    }

    public SingleNewsControl(SingleNewsActity singleNewsActity, FragmentManager supportFragmentManager) {
        this.mActity = singleNewsActity;
        mFragmentManager = supportFragmentManager;
    }

    //初始化数据
    public void initData(final ResponseListener listener) {
        //解析response
        String response = (String) mActity.getIntent().getSerializableExtra("response");
        SingleNews singleNews = (SingleNews) GsonUtil.analysis(response, SingleNews.class);
        //设置评论数和点赞数
        HttpUtil.sendHtttpRequest("http://news-at.zhihu.com/api/4/story-extra/" + singleNews.getId(), new HttpCallbackListener() {
            @Override
            public void onFinish(String response, InputStream inputStream) {
                final NewsExtra newsExtra = (NewsExtra) GsonUtil.analysis(response, NewsExtra.class);
                listener.getData(newsExtra);
            }

            @Override
            public void onError(Exception e) {

            }
        });
        //初始化Frgment列表
        mNewFragments = new ArrayList<>();
        mNewFragments.add(0, new SingleNewsFragment(singleNews.getShare_url()));
    }

    //处理标题栏按钮点击事件
    public void onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                mActity.finish();
                break;
        }
    }
}
