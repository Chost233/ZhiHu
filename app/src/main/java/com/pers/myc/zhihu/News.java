package com.pers.myc.zhihu;

/**
 * Created by Administrator on 2017/2/14.
 */

public class News {
    private String image;
    private String title;
    private String id;

    public News(String title,String image,String id){
        this.title = title;
        this.image = image;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }
}
