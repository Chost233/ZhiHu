package com.pers.myc.zhihu.model.gson;

import android.graphics.Bitmap;

import java.util.List;

/**
 * 网络请求-最新消息Gson类
 */

public class LatestNews{
    private String date;
    private List<Stories> stories;
    private List<TopStories> top_stories;

    public static class Stories {
        private List<String> images;
        private String type;
        private String id;
        private String ga_prefix;
        private String title;

        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getGa_prefix() {
            return ga_prefix;
        }
        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public List<String> getImages() {
            return images;
        }
        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStories{
        private String image;
        private String type;
        private String id;
        private String ga_perfix;
        private String title;
        private Bitmap topImage;

        public Bitmap getTopImage() {
            return topImage;
        }
        public void setTopImage(Bitmap topImage) {
            this.topImage = topImage;
        }

        public String getImage() {
            return image;
        }
        public void setImage(String image) {
            this.image = image;
        }
        public String getType() {
            return type;
        }
        public String getId() {
            return id;
        }
        public String getGa_perfix() {
            return ga_perfix;
        }
        public String getTitle() {
            return title;
        }
    }

    public String getDate() {
        return date;
    }
    public List<Stories> getStories() {
        return stories;
    }

    public void setStories(List<Stories> stories) {
        this.stories = stories;
    }

    public List<TopStories> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStories> top_stories) {
        this.top_stories = top_stories;
    }
}
