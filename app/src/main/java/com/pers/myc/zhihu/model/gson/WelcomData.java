package com.pers.myc.zhihu.model.gson;

import android.graphics.Bitmap;

import java.util.List;

/**
 * 网络请求-启动界面图片gson类
 */

//启动界面Gson类
public class WelcomData {
    private List<Creatives> creatives;

    public List<Creatives> getCreatives() {
        return creatives;
    }

    public static class Creatives{
        private String url;
        private String text;
        private String start_time;
        private List<String> impression_tracks;
        private String type;
        private String id;
        private Bitmap image = null;

        public Bitmap getImage() {
            return image;
        }
        public void setImage(Bitmap image) {
            this.image = image;
        }
        public String getUrl() {
            return url;
        }
        public String getText() {
            return text;
        }
        public String getStart_time() {
            return start_time;
        }
        public List<String> getImpression_tracks() {
            return impression_tracks;
        }
        public String getType() {
            return type;
        }
        public String getId() {
            return id;
        }
    }
}
