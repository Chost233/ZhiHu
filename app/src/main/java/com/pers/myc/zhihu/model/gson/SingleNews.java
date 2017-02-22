package com.pers.myc.zhihu.model.gson;

import java.util.List;

/**
 * 网络请求-新闻展开gson类
 */

public class SingleNews {
    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private List<String> js;
    private String ga_prefix;
    private List<String> images;
    private String type;
    private String id;
    private List<String> css;

    public String getBody() {
        return body;
    }
    public String getImage_source() {
        return image_source;
    }
    public String getTitle() {
        return title;
    }
    public String getImage() {
        return image;
    }
    public String getShare_url() {
        return share_url;
    }
    public List<String> getJs() {
        return js;
    }
    public String getGa_prefix() {
        return ga_prefix;
    }
    public List<String> getImages() {
        return images;
    }
    public String getType() {
        return type;
    }
    public String getId() {
        return id;
    }
    public List<String> getCss() {
        return css;
    }
}
