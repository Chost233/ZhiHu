package com.pers.myc.zhihu.model.gson;

import java.util.List;

/**
 * 网络请求-主题消息gson类
 */

public class ThemeNews {
    private List<Stories> stories;
    private String description;
    private String background;
    private String color;
    private String name;
    private String image;
    private List<Editors> editors;

    public List<Stories> getStories() {
        return stories;
    }
    public String getDescription() {
        return description;
    }
    public String getBackground() {
        return background;
    }
    public String getColor() {
        return color;
    }
    public String getName() {
        return name;
    }
    public String getImage() {
        return image;
    }
    public List<Editors> getEditors() {
        return editors;
    }

    public class Stories {
        List<String> images;
        String type;
        String id;
        String title;

        public List<String> getImages() {
            return images;
        }
        public String getType() {
            return type;
        }
        public String getId() {
            return id;
        }
        public String getTitle() {
            return title;
        }
    }

    public class Editors{
        String url;
        String bio;
        String id;
        String avatar;
        String name;

        public String getUrl() {
            return url;
        }
        public String getBio() {
            return bio;
        }
        public String getId() {
            return id;
        }
        public String getAvatar() {
            return avatar;
        }
        public String getName() {
            return name;
        }
    }
}
