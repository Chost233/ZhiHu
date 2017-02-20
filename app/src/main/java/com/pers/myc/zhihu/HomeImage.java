package com.pers.myc.zhihu;

import java.util.List;

/**
 * 网络请求-启动界面图片gson类
 */

//启动界面Gson类
public class HomeImage {
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
