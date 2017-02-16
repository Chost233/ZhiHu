package com.pers.myc.zhihu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 新闻菜单页面Fragment
 */

@SuppressLint("ValidFragment")
public class NewsFragment extends Fragment implements View.OnClickListener {

    //header部分
    private ImageView mImageView;
    private TextView mTextView;
    private TextView mTextView2;
    //header作者
    private ImageView[] mImageViews;
    //json返回数据
    private String mResponse;
    //返回数据类型
    private int type;
    //新闻列表
    private List<News> mNewsList;
    //新闻列表滚动
    private RecyclerView mRecyclerView;
    //recyclerviewheader
    private RecyclerViewHeader mRecyclerViewHeader;


    //type:
    //最新消息类型
    public static final int LATEST_NEWS = 0;
    //主题日报类型
    public static final int THEME_NEWS = 1;

    //header部分_图片
    private String mHeadImage;
    //header部分_文字
    private String mHeadText;
    private String mHeadText2;
    //header部分新闻数量
    private int mHeadStoriesAmount;
    //header部分新闻id
    private String mHeadStoryId;
    //主编
    private List<ThemeNews.Editors> mEditors;
    //列表新闻
    private List<LatestNews.Stories> mLStories;
    private List<ThemeNews.Stories> mTStories;


    public NewsFragment(String mResponse, int type) {
        this.mResponse = mResponse;
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container, false);

        Log.e("context:", getActivity() + "");

        //初始化视图
        initView(v);
        //json解析
        Gson gson = new Gson();
        Object analysis = null;
        switch (type) {
            case LATEST_NEWS:
                analysis = gson.fromJson(mResponse, LatestNews.class);
                break;
            case THEME_NEWS:
                analysis = gson.fromJson(mResponse, ThemeNews.class);
                break;
        }

        //初始化数据
        initData();
        //数据配置
        deployData(analysis, type);


        //配置recyclerview
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        NewsAdapter adapter = new NewsAdapter(mNewsList, getActivity());
        mRecyclerView.setAdapter(adapter);
        //将header绑定到recyclerview
        mRecyclerViewHeader.attachTo(mRecyclerView);

        //设置标题
        if (!mHeadText.equals("")) {
            mTextView.setText(mHeadText);
        }
        if (!mHeadText2.equals("")) {
            mTextView2.setText(mHeadText2);
        }
        //获取图片
        if (!mHeadImage.equals("")) {
            HttpUtil.getHttpBitmap(mHeadImage, new BitmapCallBackListener() {
                @Override
                public void onFinish(final Bitmap bitmap) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mImageView.setImageBitmap(bitmap);
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                }
            });
        }
        //获取主编头像
        if (type == THEME_NEWS) {
            for (int i = 0; i < mEditors.size(); i++) {
                final int finalI = i;
                HttpUtil.getHttpBitmap(mEditors.get(i).getAvatar(), new BitmapCallBackListener() {
                    @Override
                    public void onFinish(final Bitmap bitmap) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mImageViews[finalI].setImageBitmap(bitmap);
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
            }
        }
        return v;
    }

    private void deployData(Object analysis, int type) {
        //最新消息界面配置
        switch (type) {
            case LATEST_NEWS:
                LatestNews latestNews = (LatestNews) analysis;
                mHeadStoriesAmount = latestNews.getTop_stories().size();
                //随机标题
                Random random = new Random();
                int randomInt = 0;
                if (mHeadStoriesAmount != 0) {
                    randomInt = random.nextInt(mHeadStoriesAmount);
                }
                //配置新闻
                mLStories = latestNews.getStories();
                //配置列表
                if (mLStories != null) {
                    for (int i = 0; i < mLStories.size(); i++) {
                        mNewsList.add(new News(mLStories.get(i).getTitle(),
                                mLStories.get(i).getImages().get(0),
                                mLStories.get(i).getId()));
                    }
                }
                mHeadText = latestNews.getTop_stories().get(randomInt).getTitle();
                mHeadText2 = "今日热闻";
                mHeadImage = latestNews.getTop_stories().get(randomInt).getImage();
                mHeadStoryId = latestNews.getTop_stories().get(randomInt).getId();
                break;
            case THEME_NEWS:
                ThemeNews themenews = (ThemeNews) analysis;
                //配置新闻
                mTStories = themenews.getStories();
                //配置主编
                mEditors = themenews.getEditors();
                //配置列表
                if (mTStories != null) {
                    for (int i = 0; i < mTStories.size(); i++) {
                        News news;
                        if (mTStories.get(i).getImages() == null) {
                            news = new News(mTStories.get(i).getTitle(),
                                    null,
                                    mTStories.get(i).getId());
                        } else {
                            news = new News(mTStories.get(i).getTitle(),
                                    mTStories.get(i).getImages().get(0),
                                    mTStories.get(i).getId());
                        }
                        mNewsList.add(news);
                    }
                }
                mHeadText = themenews.getDescription();
                mHeadText2 = "主编";
                mHeadImage = themenews.getBackground();
                break;
        }
    }

    private void initData() {
        //初始化新闻列表
        mNewsList = new ArrayList<>();
        mLStories = null;
        mHeadText = "";
        mHeadText2 = "";
        mHeadImage = "";
        mHeadStoriesAmount = 0;
        mHeadStoryId = "";
    }

    private void initView(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_news_recycler_view);
        //设置recyclerview的header
        mRecyclerViewHeader = RecyclerViewHeader.fromXml(this.getContext(), R.layout.recycler_header);
        View view = mRecyclerViewHeader.getRootView();
        //初始化Imageview和Textview
        mImageView = (ImageView) view.findViewById(R.id.recycler_header_image_view);
        mTextView = (TextView) view.findViewById(R.id.recycler_header_text_view);
        mTextView2 = (TextView) view.findViewById(R.id.recycler_header_text_view_2);
        mImageViews = new ImageView[]{(ImageView) view.findViewById(R.id.recycler_header_editor_1),
                (ImageView) view.findViewById(R.id.recycler_header_editor_2),
                (ImageView) view.findViewById(R.id.recycler_header_editor_3),
                (ImageView) view.findViewById(R.id.recycler_header_editor_4),
                (ImageView) view.findViewById(R.id.recycler_header_editor_5),
                (ImageView) view.findViewById(R.id.recycler_header_editor_6),
                (ImageView) view.findViewById(R.id.recycler_header_editor_7),
        };
        initListener();
    }

    public void initListener() {
        //初始化点击监听
        mImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recycler_header_image_view:
                if (type == LATEST_NEWS && !mHeadStoryId.equals("")) {
                    String url = "http://news-at.zhihu.com/api/4/news/" + mHeadStoryId;
                    HttpUtil.sendHtttpRequest(url, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response, InputStream inputStream) {
                            Intent intent = new Intent(getActivity(),SingleNewsActity.class);
                            intent.putExtra("response",response);
                            getActivity().startActivity(intent);
                        }
                        @Override
                        public void onError(Exception e) {

                        }
                    });
                }
        }
    }
}