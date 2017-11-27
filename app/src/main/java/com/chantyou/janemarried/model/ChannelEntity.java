package com.chantyou.janemarried.model;

import java.io.Serializable;

/**
 * Created by sunfusheng on 16/4/20.
 */
public class ChannelEntity implements Serializable {

    private String title;
    private String tips;
    private String image_url;
    private int image_id;
    private int id;

    public ChannelEntity() {
    }

    public ChannelEntity(String title, String tips, String image_url) {
        this.title = title;
        this.tips = tips;
        this.image_url = image_url;
    }
    public ChannelEntity(int id,String title, String tips, int image_id) {
        this.id = id;
        this.title = title;
        this.tips = tips;
        this.image_id = image_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
