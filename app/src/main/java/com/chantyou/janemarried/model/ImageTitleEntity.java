package com.chantyou.janemarried.model;

import java.io.Serializable;

/**
 * Created by sunfusheng on 16/4/20.
 */
public class ImageTitleEntity implements Serializable {

    private String title;
    private String image_url;

    public ImageTitleEntity() {
    }


    public ImageTitleEntity(String title, String image_url) {
        this.title = title;
        this.image_url = image_url;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
