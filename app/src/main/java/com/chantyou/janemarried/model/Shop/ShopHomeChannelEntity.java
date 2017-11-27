package com.chantyou.janemarried.model.Shop;

import java.io.Serializable;

//店铺主页
public class ShopHomeChannelEntity implements Serializable {

    private String title;
    private String tips;
    private String image_url;
    private int image_id;
    private int id;
    private boolean isChecked = false;

    public ShopHomeChannelEntity() {
    }

    public ShopHomeChannelEntity(int id, String title, int image_id, boolean isChecked) {
        this.id = id;
        this.title = title;
        this.image_id = image_id;
        this.isChecked = isChecked;
    }

    public ShopHomeChannelEntity(String title, String tips, String image_url) {
        this.title = title;
        this.tips = tips;
        this.image_url = image_url;
    }

    public ShopHomeChannelEntity(int id, String title, String tips, int image_id) {
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

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
