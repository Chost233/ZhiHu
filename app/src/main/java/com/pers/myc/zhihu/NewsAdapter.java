package com.pers.myc.zhihu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

/**
 * 新闻RecyclerView适配器
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> mNewsList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {

        View newsView;
        ImageView newsImage;
        TextView newsTitle;
        RelativeLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            newsView = itemView;
            newsImage = (ImageView) itemView.findViewById(R.id.item_news_image_view);
            newsTitle = (TextView) itemView.findViewById(R.id.item_news_text_view);
            layout = (RelativeLayout) itemView.findViewById(R.id.item_news_layout);
        }
    }

    public NewsAdapter(List<News> newsList, Context context) {
        mNewsList = newsList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);

        final ViewHolder holder = new ViewHolder(view);
        holder.newsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                News news = mNewsList.get(position);
                String url = "http://news-at.zhihu.com/api/4/news/" + news.getId();
                Log.e("url:", url);
                Log.e("position:", position + "");
                Log.e("news:", news.getTitle());
                HttpUtil.sendHtttpRequest(url, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response, InputStream inputStream) {
                        Intent intent = new Intent(mContext, SingleNewsActity.class);
                        intent.putExtra("response", response);
                        mContext.startActivity(intent);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //获取item所在位置
        News news = mNewsList.get(position);
        //item图片
        final Bitmap[] imageBitmap = {null};
        //为item加载图片 -- Handler
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                holder.newsImage.setImageBitmap(imageBitmap[0]);
            }
        };
        //网络请求获取item图片并加载
        if (news.getImage() != null) {
            HttpUtil.getHttpBitmap(news.getImage(), new BitmapCallBackListener() {
                @Override
                public void onFinish(Bitmap bitmap) {
                    imageBitmap[0] = bitmap;
                    handler.sendEmptyMessage(0);
                }

                @Override
                public void onError(Exception e) {
                    Log.e("e:", e + "");
                }
            });
        } else {
            holder.newsImage.setImageResource(R.drawable.no_image);
        }
        //加载item文字
        holder.newsTitle.setText(news.getTitle());

        //列表是否滚动到底部
        if (position == getItemCount() - 1) {
            Log.e("message:", "列表滚动到底部");
        }
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }
}
